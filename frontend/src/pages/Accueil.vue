<template>
  <section class="accueilapp md-layout md-alignment-top-center">
    <div class="central md-layout-item md-size-70 md-layout md-alignment-top-center">
      <div class="head md-layout-item md-size-80 md-layout md-gutter md-alignment-top-center">
        <img
          class="logoimg md-layout-item md-extra-small-40 md-small-size-30 md-medium-size-20 md-large-size-20 md-extra-large-20 md-size-15"
          src="@/assets/logo-app.png"
          style="width:20px;height:100px;"
        >
        <div class="title md-layout-item md-size-40">
          <h1>Froggosaur</h1>
        </div>
        <!-- ou md-size-35? -->
      </div>

      <div class="search md-layout-item md-size-80">
        <search id="search" @error="notifyError"></search>
      </div>
    </div>

    <!-- <div class="feed md-layout-item md-size-100 md-layout md-alignment-center-right">
      <div class="md-layout-item md-size-20">
        <md-button class="md-raised md-primary">News Feed</md-button>
      </div>
    </div>-->

    <div class="recommended md-layout-item md-size-80">
      <carousel
        :per-page="3"
        :autoplay="true"
        :loop="true"
        :autoplayTimeout="5000"
        :navigationEnabled="true"
      >
        <slide class="slides" v-for="(recommended, index) in articles" :key="index">
          <article-slide
            :title="recommended.title"
            :authors="recommended.authors"
            :category="recommended.category"
            :year="recommended.year"
            :month="recommended.month"
            :nb-views="recommended.nb_views"
            :nb-quotes="recommended.nb_citations"
            :keywords="recommended.keywords.slice(0,4)"
            :reference="recommended.reference"
          ></article-slide>
        </slide>
      </carousel>
    </div>
  </section>
</template>

<script>
import { Carousel, Slide } from "vue-carousel";

import ArticleSlide from "@/components/accueil/ArticleSlide";

import Search from "@/components/navbar/Search";

import { getRecommanded } from "../services/api-article";

import { EventBus, EVENT_APP_MESSAGE } from "@/services/event-bus.js";

export default {
  name: "Accueil",
  components: {
    Carousel,
    Slide,
    ArticleSlide,
    Search
  },
  data() {
    return {
      searchInput: null,
      msg: "",
      articles: {}
    };
  },

  created() {
    this.fetchRecommanded();
  },

  methods: {
    fetchRecommanded() {
      return getRecommanded().then(data => {
        this.articles = data;
      });
    },
    notifyError(msg) {
      EventBus.$emit(EVENT_APP_MESSAGE, { type: "error", msg });
    }
  }
};
</script>

<style scoped>
.head {
  margin-top: 2%;
}

h1 {
  margin-left: 47px;
  margin-top: 35px;
}

.search {
  margin-top: 2%;
}

.feed {
  margin-top: 1%;
}

.recommended {
  margin-top: 2%;
}

.slides {
  margin: 3px;
}
</style>
