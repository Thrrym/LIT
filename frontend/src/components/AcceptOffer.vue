<template>
  <div>
    <ServerComAcceptOffer
        ref="ServerComAcceptOffer"
        v-on:requestResponse="requestSuccess"></ServerComAcceptOffer>
    <b-modal ref="acceptSuccessModal" ok-only titel="Offer accepted" >
      You accepted the offer and your entry was updated.
    </b-modal>
  </div>
</template>

<script>
import ServerComAcceptOffer from "@/components/ServerComAcceptOffer";

export default {
  name: "AcceptOffer",
  components: {
    ServerComAcceptOffer,
  },
  data() {
    return {
      requestResponse: {},
      responseText: "",
      responseJson: {},
      offerObjects: [],
    };
  },
  methods: {
    triggerAcceptOffer: function (offerId) {
      this.$refs.ServerComAcceptOffer.trigger(offerId);
    },
    requestSuccess: function () {
      /*this.$emit("acceptSuccess");*/
      /*this.requestResponse = response;
      this.responseText = response.responseText;
      this.responseJson = JSON.parse(this.responseText);*/
      this.$emit("acceptSuccess");
      this.$refs.acceptSuccessModal.show();
    },
  },
};
</script>

<style scoped></style>