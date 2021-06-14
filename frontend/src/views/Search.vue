<template>
<div>
  <div class="row justify-content-center" id="searchbar">
    <div class="col-8">
      <div class="input-group">
        <input type="text" class="form-control" placeholder="Search..." id="searchinput">
        <div class="input-group-append">
          <button class="btn btn-primary" type="button">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
              <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
            </svg>
          </button>
        </div>
      </div>
    </div>
  </div>
  <div class="row justify-content-center">
    <div class="col-8">
      <SearchResult type="Article" info="AUTHOR, TITLE, JOURNAL, YEAR, VOLUME" likes=17 style="margin-bottom: .4em;"></SearchResult>
      <SearchResult type="Book" info="AUTHOR/EDITOR, TITLE, PUBLISHER, YEAR" likes=2 style="margin-bottom: .4em;"></SearchResult>
      <SearchResult type="Booklet" info="TITLE" liked=true likes=1337 style="margin-bottom: 1em;"></SearchResult>
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
}
</script>

<style>
#searchbar {
  margin-top: 2em;
  margin-bottom: 2em;
}

@keyframes minimalfocus {
  100%{box-shadow:none; outline: 0 none;};
}

#searchinput:focus{
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