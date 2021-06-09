<template>
  <!-- Component: Prepare new entry for server communication. -->
  <div>
    <ServerCom
      ref="com"
      v-bind:requestType="setRequestType"
      v-bind:requestContent="getPreparedNewEntry"
    ></ServerCom>
  </div>
</template>

<script>
import ServerCom from "@/components/ServerCom.vue";

export default {
  name: "NewEntryPrep",
  components: {
    ServerCom,
  },
  props: {
    selectedType: {
      required: true,
    },
    newEntry: {
      required: true,
    },
  },
  data() {
    return {
      preparedNewEntry: "",
    }
  },
  methods: {
    preparation: function () {
      //let type = "bibtex_" + this.selectedType;
      // this.preparedNewEntry = this.newEntry.map(function (elem) {
      //   if (elem.required === true) return true;
      // });
      //console.log(this.newEntry);

      var newObject = new Object();
      for (let index = 0; index < this.newEntry.length; index++) {
        const element = this.newEntry[index];
        newObject[element.name] = element.content;
      };
      this.preparedNewEntry = newObject;

      this.$refs.com.triggerServerCom();
    }
  },
  computed: {
    getPreparedNewEntry: function () {
      return this.preparedNewEntry;
    },
    setRequestType: function () {
      return "newEntry";
    },
  },
}
</script>

<style>

</style>