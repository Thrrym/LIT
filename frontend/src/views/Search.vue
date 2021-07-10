<template>
<div>
  <div class="row justify-content-center" id="searchbar">
    <div class="col-8">
      <div class="input-group">
        <input v-on:keyup.enter="startSearch" type="text" class="form-control minifocus" placeholder="Search..." id="searchinput">
        <div class="input-group-append">
          <button v-on:click="toggle_filter" class="btn btn-primary" type="button">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-filter" viewBox="0 0 16 16">
              <path d="M6 10.5a.5.5 0 0 1 .5-.5h3a.5.5 0 0 1 0 1h-3a.5.5 0 0 1-.5-.5zm-2-3a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zm-2-3a.5.5 0 0 1 .5-.5h11a.5.5 0 0 1 0 1h-11a.5.5 0 0 1-.5-.5z"/>
            </svg>
          </button>
        </div>
      </div>
    </div>
  </div>
  <div v-if="showFilter" class="row justify-content-center" id="searchbar_filter">
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
  </div>
  <div class="row justify-content-center" id="searchresults">
    <div class="col-8">
      <SearchResult type="Article" info="AUTHOR, TITLE, JOURNAL, YEAR, VOLUME" :likes=17 style="margin-bottom: .4em;"></SearchResult>
      <SearchResult type="Book" info="AUTHOR/EDITOR, TITLE, PUBLISHER, YEAR" :likes=2 style="margin-bottom: .4em;"></SearchResult>
      <SearchResult type="Booklet" info="TITLE" :liked=true :likes=1337 style="margin-bottom: 1em;"></SearchResult>
    </div>
  </div>
</div>
</template>

<script>
import SearchResult from "@/components/SearchResult.vue";

export default {
    name: "Search",
    components: {
        SearchResult,
    },
    data() {
      return {
        showFilter: false,
        userFilter: '',
        typeFilter: '',
        ee: false,
        eeid: -1
      }
    },
    methods: {
      startSearch() {
        var search_text = document.getElementById('searchinput').value;
        this.meme_check(search_text);
        if (search_text === 'r/ich_iel') window.location.href = "https://www.reddit.com/r/ich_iel/";
      },
      meme_check(search_text) {
        var meme_src = '';
        var seeid = this.eeid;

        if (['jojo', 'giornos theme', 'giorno\'s theme'].includes(search_text.toLowerCase()) && this.eeid != 0) {
          this.eeid = 0;
          meme_src = '2MtOpB5LlUA';
        }
        else if (['initial d', 'deja vu'].includes(search_text.toLowerCase()) && this.eeid != 1) {
          this.eeid = 1;
          meme_src = 'dv13gl0a-FA';
        }
        else if (['megalovania'].includes(search_text.toLowerCase()) && this.eeid != 2) {
          this.eeid = 2;
          meme_src = 'wDgQdr8ZkTw';
        }
        else if (['rickroll'].includes(search_text.toLowerCase()) && this.eeid != 3) {
          this.eeid = 3;
          meme_src = 'dQw4w9WgXcQ';
        }
        else {
          this.eeid = -1;
        }

        if (this.eeid != seeid && this.eeid != -1){
          if (this.ee) {
            document.getElementById('EasterEgg').remove();
          }

          var rrr = document.createElement('div');
          rrr.classList.add('row', 'justify-content-center');
          rrr.style.marginBottom = '2em';
          rrr.id = 'EasterEgg'; 

          var rrc = document.createElement('div');
          rrc.classList.add('col-8');

          var rre = document.createElement('div');
          rre.classList.add('embed-responsive', 'embed-responsive-16by9');

          var rri = document.createElement('iframe');
          rri.classList.add('embed-responsive-item');
          rri.src = 'https://www.youtube.com/embed/' + meme_src + '?showinfo=0&rel=0';
          rri.setAttribute('allowFullScreen', '');
          rri.setAttribute('allow', 'autoplay');

          rre.appendChild(rri);
          rrc.appendChild(rre);
          rrr.appendChild(rrc);

          var target = document.getElementById('searchresults');
          target.parentNode.insertBefore(rrr, target);

          this.ee = true;
        }

        if (this.ee && this.eeid == -1) {
            document.getElementById('EasterEgg').remove();
            this.ee = false;
        }
      },
      toggle_filter() {
        this.showFilter = !this.showFilter;
      }
    }
}
</script>

<style>
#searchbar {
  margin-top: 2em;
  margin-bottom: 1em;
}

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

.justify-content-center {
  margin-left: 0%;
  margin-right: 0%;
}
</style>