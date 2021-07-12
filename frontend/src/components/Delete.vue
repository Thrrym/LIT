<template>
  <div>
    <ServerComDelete
        ref="ServerComDelete"
        v-on:requestResponse="deleteSuccess">
    </ServerComDelete>
    <b-modal ref="confirmModal" title="Confirm delete" v-on:ok="deleteConfirmed">Please, verify the deletion of the Lit object.</b-modal>
    <b-modal ref="deleteSuccessModal" titel="You deleted the entry" ok-only></b-modal>
  </div>
</template>

<script>
import ServerComDelete from "@/components/ServerComDelete";

export default {
  name: "Delete",
  components: {
    ServerComDelete,
  },
  data() {
    return {
      requestResponse: {},
      responseText: "",
      responseJson: {},
      objectId: "",
    };
  },
  methods: {
    trigger: function (objectId) {
      this.objectId = objectId;
      this.$refs.confirmModal.show();
    },
    deleteConfirmed: function () {
      this.$refs.ServerComDelete.trigger(this.objectId);
    },
    deleteSuccess: function (response) {
      this.requestResponse = response;
      this.responseText = response.responseText;
      this.$emit("deleteSuccess");
      this.$refs.deleteSuccessModal.show();
    },
  },
};
</script>

<style scoped></style>