// -------------------------------------------------------------------
// Dependencies

// Import
import Vue from 'vue';

// Mine
import { getAuthHeaders } from './api';

// -------------------------------------------------------------------
// Properties

// -------------------------------------------------------------------
// Exports

export function getArticleByReference(reference) {
  return Vue.http.get('/api/article/' + reference).then(res => res.body);
}

export function createArticle(article) {
  const headers = getAuthHeaders();
  return Vue.http.post('/api/article', article, headers).then(res => res.body);
}
