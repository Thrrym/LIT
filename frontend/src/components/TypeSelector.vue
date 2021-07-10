<template>
  <div>
    <b-dropdown id="dropdown-1" v-bind:text="getButtonText" class="m-md-2">
      <b-dropdown-item
        v-for="type in entryTypes"
        v-bind:key="type"
        v-on:click="setSelectedType(type)"
      >
        {{ type }}
      </b-dropdown-item>
    </b-dropdown>
  </div>
</template>

<script>
export default {
  name: "TypeSelector",
  data() {
    return {
      selectedType: "",
    };
  },
  props: {
    entryTypes: {
      required: true,
    },
  },
  methods: {
    setSelectedType: function (type) {
      this.selectedType = type;
      this.$emit("selectedType", this.selectedType);
    },
  },
  computed: {
    getButtonText: function () {
      if (this.selectedType === "") {
        return "Select your type of entry";
      } else {
        return this.selectedType;
      }
    },
  },
};
</script>

<style></style>
