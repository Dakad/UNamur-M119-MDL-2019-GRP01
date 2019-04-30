<template>
  <div class="article">
    <md-card v-for="article in list" :key="article.reference">
      <md-card-header>
        <div class="title">
          <router-link :to="{ name: 'articleDetails', params: { reference: article.reference }}">
            <h1>{{article.title}}</h1>
          </router-link>
        </div>
      </md-card-header>
      <md-card-content>
        <div class="info">
          <p>Written By</p>
          <div class="author">
            <md-list v-for="author in article.authors" v-bind:key="author">
              <md-list-item></md-list-item>
              <!-- <md-item href="/">{{author}}</md-item> -->
            </md-list>
          </div>
          <div class="keyword"></div>
          <div class="date">
            <p>Published on the</p>
            <h4>{{article.year}}</h4>
          </div>
        </div>
      </md-card-content>
    </md-card>
    <Pagination v-model="currentPage" :page-count="page['total_size']"/>
  </div>
</template>

<script>
import { Pagination } from "@/components";

export default {
  name: "ArticleList",
  components: {
    Pagination
  },
  props: {
    list: {
      type: Array,
      default: _ => []
    },
    meta: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      currentPage: 1
    };
  },
  watch: {
    currentPage: function() {
      this.$emit("pagination", this.currentPage);
    }
  },
  computed: {
    articles() {
      return this.list;
    },
    page() {
      return this.meta || {};
    }
  },
  methods: {}
};
</script>

<style scoped>
.md-list {
  margin: 10px;
  display: inline-block;
}
</style>