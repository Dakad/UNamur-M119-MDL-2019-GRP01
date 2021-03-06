<template>
  <div class="general">
    <div class="left">
      <div class="md-layout">
        <div class="leftIcon">
          <md-icon class="material-icons">insert_photo</md-icon>
        </div>
        <div class="zoneText">
          <md-field>
            <label>Change your avatar</label>
            <md-input v-model="profile.avatar_url"/>
          </md-field>
        </div>

        <div class="leftIcon">
          <md-icon class="material-icons">home</md-icon>
        </div>
        <div class="zoneText">
          <md-autocomplete v-model="selected.university" :md-options="listUniversities">
            <label>Change your university</label>
          </md-autocomplete>
        </div>

        <div class="leftIcon">
          <md-icon class="material-icons">keyboard</md-icon>
        </div>
        <div class="zoneText">
          <md-autocomplete v-model="selected.domain" :md-options="listDomains">
            <label>Change your domain</label>
          </md-autocomplete>
        </div>
      </div>
    </div>

    <div class="right">
      <div class="leftIcon">
        <md-icon class="material-icons">email</md-icon>
      </div>
      <div class="zoneText">
        <md-field>
          <label>Change your email address</label>
          <md-input v-model="profile.email"></md-input>
        </md-field>
      </div>

      <div class="leftIcon">
        <md-icon class="material-icons">group</md-icon>
      </div>
      <div class="zoneText">
        <md-field>
          <label>Change your research groups</label>
          <md-input v-model="selected['research_groups']" md-autogrow></md-input>
        </md-field>
      </div>

      <md-field>
        <label>Change your bio</label>
        <md-textarea v-model="profile.bio" md-counter="250"></md-textarea>
      </md-field>
    </div>

    <div class="centeredButton">
      <md-button class="md-raised md-primary" @click="wantToChange = true">Save changes</md-button>
    </div>
    <!--  ask if ok to change  -->

    <md-dialog :md-active.sync="wantToChange">
      <md-dialog-title>Are you sure to change your information</md-dialog-title>
      <md-content class="md-layout md-alignment-center-space-around">
        <md-button class="md-dense md-raised" @click="wantToChange = false">No</md-button>

        <md-button class="md-dense md-raised md-primary" :md-ripple="false" @click="updateData">Yes</md-button>
      </md-content>
    </md-dialog>
  </div>
</template>

<script>
import {
  postModificationProfile,
  getProfileBase,
  getProfileInfoPro,
  getProfileSocial
} from "@/services/api-user";
import {
  EventBus,
  EVENT_USER_LOGOUT,
  EVENT_PROFILE_UPDATED,
  EVENT_BYE_REDIRECTION,
  EVENT_APP_MESSAGE
} from "@/services/event-bus.js";

export default {
  name: "ProfileUpdate",
  data() {
    return {
      wantToChange: false,
      sending: false,
      selected: {
        university: null,
        domain: null,
        research_groups: null
      },
      profile: {}
    };
  },

  computed: {
    listDomains() {
      return [
        "Computer Science",
        "Literature",
        "Chemistry",
        "Physics",
        "Biology",
        "Medicine",
        "Economics",
        "Psychology",
        "Laws",
        "Mathematics",
        "Veterinary",
        "History",
        "Engineering",
        "Language",
        "Unknown"
      ];
    },

    listUniversities() {
      return ["UNamur", "Ulg", "UMons", "Kul", "Oxford", "MIT"];
    },
    listResearchGroups() {
      return ["Nadi", "IRIDIA", "BRUH"];
    }
  },

  created() {
    // Fetch the needed profile info (base, social, info)
    this.fetchProfileInfo();

    EventBus.$on(EVENT_USER_LOGOUT, _ => {
      this.$router.replace({ name: "accueil" }, function onComplete() {
        EventBus.$emit(EVENT_BYE_REDIRECTION, true);
      });
    });
  },
  methods: {
    fetchProfileInfo() {
      getProfileBase().then(data => {
        console.log(data);
        this.profile = Object.assign({}, data);

        if (this.profile.university != null) {
          this.$set(
            this.selected,
            "university",
            this.profile.university.abbreviation
          );
        }

        if (this.profile.domain == null) {
          this.profile.domain = "Unknown";
        }
        this.$set(this.selected, "domain", this.profile.domain);
      });
      getProfileInfoPro().then(data => {
        if (data.researchGroup) {
          this.profile["researchGroup"] = data.researchGroup || "";
          this.$set(
            this.selected,
            "research_groups",
            this.profile.researchGroup.join(",")
          );
        }
      });
      getProfileSocial().then(data => {
        console.log(data);
        this.profile["bio"] = data.bio;
      });
    },

    updateData() {
      this.sending = true;
      let dataToSend = Object.assign(this.profile, this.selected, {
        researchGroup: undefined,
        research_groups: this.selected.research_groups
          .split(",")
          .filter(r => r.length)
      });
      // dataToSend["university"] = this.profile.university.abbreviation;
      postModificationProfile(dataToSend)
        .then(_ => {
          this.wantToChange = false;
          this.sending = false;
          EventBus.$emit(EVENT_PROFILE_UPDATED, "Profile successfully updated");
          this.$router.push({ name: "myProfile" });
        })
        .catch(({ body }) => {
          const { message } = body;
          this.wantToChange = false;
          this.sending = false;
          EventBus.$emit(EVENT_APP_MESSAGE, {
            type: "error",
            message
          });
        });
    }
  }
};
</script>

<style scoped>
.left {
  float: left;
  width: 41%;
  margin: 2%;
}
.right {
  float: left;
  width: 41%;
  margin: 2%;
}
.leftIcon {
  float: left;
  padding-top: 4%;
  width: 10%;
  margin: 3%;
}
.zoneText {
  float: left;
  width: 83%;
}

.centeredButton {
  float: left;
  margin-left: 38%;
}
</style>
