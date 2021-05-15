<template>
  <div>
    <b-dropdown id="dropdown-1" text="Select your type of entry" class="m-md-2">
      <b-dropdown-item
        v-for="x in entryTypes"
        v-bind:key="x.type"
        v-on:click="setSelectedType(x.type)"
      >
        {{ x.type }}
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
      requiered: true,
    },
  },
  methods: {
    setSelectedType: function (type) {
      this.selectedType = type;
      this.$emit("selectedType", this.selectedType);
    },
  },
};
</script>

<style></style>
