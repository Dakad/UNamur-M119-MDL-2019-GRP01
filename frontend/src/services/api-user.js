// -------------------------------------------------------------------
// Dependencies

// Import
import Vue from 'vue';
// -------------------------------------------------------------------
// Properties
const Api = Vue.http;

const Storage = localStorage;

const isInStorage = key => Storage.hasOwnProperty(key);

const getFromStorage = key => JSON.parse(Storage.getItem(key));

const addToStorage = (key, item) => Storage.setItem(key, JSON.stringify(item));

/**
 * Retrieve the list either from the CacheStorage or directly through the API.
 * @param {String} url API url where to get the list.
 * @returns {Promise} containing the resolved list.
 */
const getList = url => {
  const key = 'API_LIST_FOR_' + url.replace(/\//g, '').toUpperCase();
  if (isInStorage(key)) {
    return Promise.resolve(getFromStorage(key));
  } else {
    return Api.get(url).then(({ data }) => {
      addToStorage(key, data);
      return data;
    });
  }
};

// -------------------------------------------------------------------
// Exports

export function postLogin(credentials) {
  return Vue.http.post('api/login', credentials).then(data => console.log(data));
}

export function postSignin(data) {
  return Vue.http.post('api/signin', data).then();
}