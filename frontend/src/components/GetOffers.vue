<template>
  <div>
    <ServerComGetOffers
        ref="ServerComGetOffers"
        v-on:requestResponse="requestSuccess"></ServerComGetOffers>
  </div>
</template>

<script>
import ServerComGetOffers from "@/components/ServerComGetOffers";
export default {
  name: "GetOffers",
  components: {ServerComGetOffers},
  data() {
    return {
      requestResponse: {},
      responseText: "",
      responseJson: {},
      offerObjects: [],
    };
  },
  methods: {
    triggerGetOffers: function () {
      this.$refs.ServerComGetOffers.triggerGetOffers();
    },
    requestSuccess: function (response) {
      this.requestResponse = response;
      this.responseText = response.responseText;
      this.responseJson = JSON.parse(this.responseText);
      this.offerObjects = this.responseJson.orderedItems;
      this.$emit("getOffersSuccess", this.offerObjects);
    },
  },
};
</script>

<style scoped></style>