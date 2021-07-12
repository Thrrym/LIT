<template>
  <div>
    <ServerComRejectOffer
        ref="ServerComRejectOffer"
        v-on:requestResponse="requestSuccess"></ServerComRejectOffer>
    <b-modal ref="rejectSuccessModal" titel="You rejected the offer" ok-only></b-modal>
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
    requestSuccess: function (response) {
      this.requestResponse = response;
      this.responseText = response.responseText;
      this.responseJson = JSON.parse(this.responseText);
      this.$refs.acceptSuccessModal.show();
    },
  },
};
</script>

<style scoped></style>