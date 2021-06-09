<template>
  <!-- Component: Prepare new entry for server communication. -->
  <div>
    <ServerCom
      ref="com"
      v-bind:requestType="setRequestType"
      v-bind:requestContent="this.preparedNewEntry"
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
      let newObject = new Object();
      //for (let index = 0; index < this.newEntry.length; index++) {
      for (const property in this.newEntry) {
        //const element = this.newEntry[index];
        //newObject[element.name] = element.content;
        newObject[property] = this.newEntry[property];
      };

      newObject["type"] = this.selectedType;
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