<template>
<div>
  <b-container style="margin-top: 2em; margin-bottom: 1em;">
    <b-row>
      <b-col xs="8">
        <b-input-group>
          <b-form-input v-on:keyup.enter="startSearch" class="minifocus" type="search" placeholder="Search..." v-model="query"></b-form-input>
          <b-input-group-append>
            <b-button :pressed.sync="showFilter" variant="primary"><b-icon-filter aria-hidden="true" font-scale="1.25" shift-v="-1"></b-icon-filter></b-button>
          </b-input-group-append>
        </b-input-group>
      </b-col>
    </b-row>
  </b-container>

  <!--<div v-if="showFilter" class="row justify-content-center" id="searchbar_filter">
    <div class="col-8">
      <div span class="card text-left border-primary">
        <div class="card-body">
          <div class="row justify-content-center">
            <div class="col-5">
              <div class="input-group">
                <div class="input-group-prepend">
                  <span class="input-group-text" id="basic-addon1">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill" viewBox="0 0 16 16">
                      <path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"></path>
                    </svg>
                  </span>
                </div>
                <input type="text" class="form-control minifocus" placeholder="Username">
              </div>
            </div>
            <div class="col-5">
              <div class="input-group">
                <div class="input-group-prepend">
                  <span class="input-group-text" id="basic-addon1">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-book-fill" viewBox="0 0 16 16">
                      <path d="M8 1.783C7.015.936 5.587.81 4.287.94c-1.514.153-3.042.672-3.994 1.105A.5.5 0 0 0 0 2.5v11a.5.5 0 0 0 .707.455c.882-.4 2.303-.881 3.68-1.02 1.409-.142 2.59.087 3.223.877a.5.5 0 0 0 .78 0c.633-.79 1.814-1.019 3.222-.877 1.378.139 2.8.62 3.681 1.02A.5.5 0 0 0 16 13.5v-11a.5.5 0 0 0-.293-.455c-.952-.433-2.48-.952-3.994-1.105C10.413.809 8.985.936 8 1.783z"/>
                    </svg>
                  </span>
                </div>
                <select class="custom-select minifocus" id="inputGroupSelect01">
                  <option value="0" selected>Type...</option>
                  <option value="1">Article</option>
                  <option value="2">Book</option>
                  <option value="3">Booklet</option>
                </select>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>-->

  <b-container id="meme-content">
  </b-container>

  <b-container id="content" style="margin-bottom: 2em;">
    <b-row>
      <b-col xs="8">
        <b-tabs pills vertical align="center" style="margin-bottom: 15px">
          <b-tab active @click='tab = "literature"'>
            <template #title>
              <b-icon-journal-bookmark-fill aria-hidden="true" font-scale="1.25" shift-v="-1" style="margin-right: 5px;"></b-icon-journal-bookmark-fill>Literature
            </template>
            <SearchLiteratureResult v-for="l_item in listed_literature" v-bind:key="l_item" v-bind:title="l_item.title" v-bind:type="l_item.type" v-bind:likes="l_item.likes"></SearchLiteratureResult>
          </b-tab>
          <b-tab @click='tab = "user"'>
            <template #title>
              <b-icon-person-fill aria-hidden="true" font-scale="1.25" shift-v="-1" style="margin-right: 5px;"></b-icon-person-fill>User
            </template>
            <SearchUserResult v-for="u_item in listed_users" v-bind:key="u_item" v-bind:name="u_item.name" v-bind:follow_id="u_item.id" v-bind:follower="u_item.follower" v-bind:posts="u_item.posts"></SearchUserResult>
          </b-tab>
        </b-tabs>
      </b-col>
    </b-row>
  </b-container>
</div>
</template>

<script>
export default {
    name: "Search",
    components: {
      SearchUserResult: () => import('@/components/SearchUserResult.vue'),
      SearchLiteratureResult: () => import('@/components/SearchLiteratureResult.vue')
    },
    data() {
      return {
        query: null,
        tab: 'literature',
        listed_literature: [],
        listed_users: [],

        showFilter: false,
        userFilter: '',
        typeFilter: '',

        meme_mode: false,
        memeid: -1
      }
    },
    methods: {
      startSearch() {
        if (this.query === 'r/ich_iel') window.location.href = "https://www.reddit.com/r/ich_iel/";
        this.meme_check();

        var component = this;

        var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
        var httpRequest = new XMLHttpRequest();

        httpRequest.open('POST', this.$store.state.proxyBackendUrl + 'search/', true);
        httpRequest.setRequestHeader(
          "Content-Type",
          "application/json;charset=UTF-8"
        );
        httpRequest.onreadystatechange = function () {
          if (httpRequest.readyState === httpRequest.DONE) {
            let status = httpRequest.status;
            if (status === 0 || (status >= 200 && status < 400)) {
              component.callbackResponse(httpRequest.responseText);
            } else {
              component.callbackError();
            }
          }
        };

        let jsonObject = {
          searchType: this.tab,
          query: this.query,
          date: {}
        };
        httpRequest.send(JSON.stringify(jsonObject));
      },
      callbackResponse(request) {
        var obj = JSON.parse(request);
        if (obj["searchType"] === 'literature') {
          this.listed_literature = obj['items'];
        }
        else if (obj["searchType"] === 'user') {
          this.listed_users = obj['items'];
        }
      },
      callbackError() {

      },
      meme_check() {
        var meme_src;
        var s_memeid = this.memeid;

        if (['jojo', 'giornos theme', 'giorno\'s theme'].includes(this.query.toLowerCase())) {
          this.memeid = 0;
          meme_src = '2MtOpB5LlUA';
        }
        else if (['initial d', 'deja vu'].includes(this.query.toLowerCase())) {
          this.memeid = 1;
          meme_src = 'dv13gl0a-FA';
        }
        else if (['megalovania'].includes(this.query.toLowerCase())) {
          this.memeid = 2;
          meme_src = 'wDgQdr8ZkTw';
        }
        else if (['rickroll'].includes(this.query.toLowerCase())) {
          this.memeid = 3;
          meme_src = 'dQw4w9WgXcQ';
        }
        else {
          this.memeid = -1;
        }

        if (this.memeid != s_memeid && this.memeid != -1){
            this.meme_mode = true;
            document.getElementById('meme-content').innerHTML = '';
            var target = document.getElementById('meme-content');
            target.insertAdjacentHTML('beforeend', '<div class="row"><div xs="8" class="col"><div class="embed-responsive embed-responsive-16by9"><iframe src="https://www.youtube.com/embed/' + meme_src + '?rel=0" allowfullscreen="allowfullscreen" class="embed-responsive-item"></iframe></div></div></div>')
            target.style.marginBottom = '2em';
        }

        if (this.meme_mode && this.memeid == -1) {
          this.meme_mode = false;
          document.getElementById('meme-content').innerHTML = '';
          target.style.marginBottom = '0em';
        }
      }
    }
}
</script>

<style>
#searchbar_filter {
  margin-bottom: 2em;
}

@keyframes minimalfocus {
  100%{box-shadow:none; outline: 0 none;};
}

.minifocus:focus{
  animation-name: minimalfocus;
  animation-duration: 1s;
  animation-delay: 1s;
  animation-fill-mode: forwards;
  animation-iteration-count: 1;
}

.btn:focus {
  box-shadow: none;
}
</style>