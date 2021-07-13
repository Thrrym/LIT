<template>
  <b-container style="margin-bottom: 5px">
    <b-card no-body style="padding: 15px">
      <b-row>
        <b-col cols="1"><svg width="46" height="46" v-bind:data-jdenticon-value="name"></svg></b-col>
        <b-col cols="9" class="text-left">
          <h5 style="color: dark; margin-bottom: 4px">{{ name }}</h5>
          <h6 style="color: gray; margin: 0px">{{ follower +  ' Follower  | ' + posts + ' Posts' }}</h6>
        </b-col>
        <b-col cols="2">
          <b-button variant="primary"><b-icon-person-plus-fill aria-hidden="true" font-scale="1.25" shift-v="-1"></b-icon-person-plus-fill></b-button>
        </b-col>
      </b-row>
    </b-card>
  </b-container>
</template>

<script>
export default {
  name: "SearchUserResult",
  props: {
      name: {
        type: String,
        default: 'User'
      },
      follow_id: {
        type: String,
        default: ''
      },
      follower: {
        type: Number,
        default: 0
      },
      posts: {
        type: Number,
        default: 0
      }
  },
  methods: {
    followUser() {
    }
  },
  mounted() {
    let jdenticon = document.createElement('script');
    jdenticon.setAttribute('src', 'https://cdn.jsdelivr.net/npm/jdenticon@3.1.0/dist/jdenticon.min.js');
    jdenticon.setAttribute('async', '');
    jdenticon.setAttribute('integrity', 'sha384-VngWWnG9GS4jDgsGEUNaoRQtfBGiIKZTiXwm9KpgAeaRn6Y/1tAFiyXqSzqC8Ga/');
    jdenticon.setAttribute('crossorigin', 'anonymous');
    document.head.appendChild(jdenticon);
  }
}
</script>

<style>
</style>