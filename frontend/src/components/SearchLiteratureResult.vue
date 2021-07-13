<template>
  <b-container style="margin-bottom: 5px">
    <b-card no-body style="padding: 15px">
      <b-row>
        <b-col cols="10" class="text-left">
          <h5 style="color: dark; margin-bottom: 4px">{{ title }} <span style="font-size: 55%">{{ type }}</span></h5>
        </b-col>
        <b-col cols="2">
          <b-button-group>
            <b-button variant="primary">
              <b-icon-bookmark-heart v-if="!liked" aria-hidden="true"></b-icon-bookmark-heart>
              <b-icon-bookmark-heart-fill v-if="liked" aria-hidden="true"></b-icon-bookmark-heart-fill>
            </b-button>
            <b-button variant="primary">
              <b-icon-clipboard aria-hidden="true" font-scale="1" shift-v="1" style="margin-left: 2px"></b-icon-clipboard>
            </b-button>
          </b-button-group>
        </b-col>
      </b-row>
      <b-row>
        <b-col cols="10" class="text-left">
          <h6 style="color: dark; margin-bottom: 4px">{{ info }}</h6>
        </b-col>
      </b-row>
      <b-row>
        <b-col class="text-left">
          <h6 style="color: gray; margin: 0px">{{ likes }} Likes | Posted by {{ generator }}</h6>
        </b-col>
      </b-row>
    </b-card>
  </b-container>
</template>

<script>
export default {
  name: "SearchLiteratureResult",
  props: {
      title: {
        type: String,
        default: 'Title'
      },
      type: {
        type: String,
        default: 'Type'
      },
      info: {
        type: String,
        default: 'info'
      },
      liked: {
        type: Boolean,
        default: false
      },
      likes: {
        type: Number,
        default: 0
      },
      generator: {
        type: String,
        default: ''
      }
  },
  methods: {
    copy() {
    },
    likesPost() {
    }
  }
}
</script>

<style>
</style>