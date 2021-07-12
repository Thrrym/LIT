<template>
<b-container style="margin-bottom: 5px">
  <b-card no-body style="padding: 15px">
    <b-row>
      <b-col cols="1"><b-img fluid rounded src="https://picsum.photos/250/250/?image=54" alt="Image 1"></b-img></b-col>
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
        default: 'USER'
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
  },
}
</script>

<style>
</style>