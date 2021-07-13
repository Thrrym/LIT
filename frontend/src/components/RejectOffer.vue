<template>
  <div>
    <ServerComRejectOffer
        ref="ServerComRejectOffer"
        v-on:requestResponse="requestSuccess"></ServerComRejectOffer>
    <b-modal ref="rejectSuccessModal" ok-only title="Rejected offer">
      You rejected the offer.
    </b-modal>
  </div>
</template>

<script>
import ServerComRejectOffer from "@/components/ServerComRejectOffer";

export default {
  name: "RejectOffer",
  components: {
    ServerComRejectOffer,
  },
  data() {
    return {
      offerId: "",
      requestResponse: {},
      responseText: "",
      responseJson: {},
      offerObjects: [],
    };
  },
  methods: {
    triggerRejectOffer: function (offerId) {
      this.offerId = offerId;
      this.$refs.ServerComRejectOffer.trigger(offerId);
    },
    requestSuccess: function () {
      /*this.requestResponse = response;
      this.responseText = response.responseText;
      this.responseJson = JSON.parse(this.responseText);*/
      this.$emit("rejectSuccess");
      this.$refs.rejectSuccessModal.show();
    },
  },
};
</script>

<style scoped></style>