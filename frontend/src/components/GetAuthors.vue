<template>
  <div>
    <ServerComGetAuthors
        ref="ServerComGetAuthors"
        v-on:requestResponse="requestSuccess"></ServerComGetAuthors>
  </div>
</template>

<script>
import ServerComGetAuthors from "@/components/ServerComGetAuthors";
export default {
  name: "GetAuthors",
  components: {ServerComGetAuthors},
  data() {
    return {
      requestResponse: {},
      responseText: "",
      responseJson: {},
      authorObjects: [],
      selectOptions: [],
    };
  },
  methods: {
    triggerGetAuthors: function () {
      this.$refs.ServerComGetAuthors.triggerGetAuthors();
    },
    requestSuccess: function (response) {
      this.requestResponse = response;
      this.responseText = response.responseText;
      this.responseJson = JSON.parse(this.responseText);
      this.authorObjects = this.responseJson.items;
      this.selectOptions = this.prepSelectOptions();
      this.$emit("getAuthorsSuccess", this.selectOptions);
    },
    prepSelectOptions: function () {
      let selectOptions = [];
      for (const author in this.authorObjects) {
        let authorOptionObject = {};
        authorOptionObject["value"] = this.authorObjects[author].id;
        authorOptionObject["text"] = this.authorObjects[author].firstName + " " + this.authorObjects[author].lastName;
        selectOptions.push(authorOptionObject);
      }
      return selectOptions;
    },
  },
  computed: {},
};
</script>

<style scoped></style>