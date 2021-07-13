<template>
  <div>
    <b-modal ref="modal-1" v-bind:title="getModalTitle" v-on:ok="createNewAuthor" size="lg">
      <b-container>
        <!-- Create the form the get the information for the selected type. -->
        <b-form>
          <b-card bg-varint="light" style="border: none">
            <b-form-group
              label-cols-lg="3"
              label="Required fields"
              label-size="lg"
              lable-class="font-weight-bold pt-0"
            >
              <!-- Create the individual field based on the provided objects. -->
              <b-form-group
                v-for="field in getRequiredFields"
                v-bind:key="field.id"
                v-bind:label="field.label"
                label-cols-sm="3"
                label-align-sm="right"
              >
                <b-form-input
                  v-model="field.content"
                  v-bind:required="field.required"
                ></b-form-input>
              </b-form-group>
            </b-form-group>
          </b-card>

<!--          <b-button
            variant="outline-primary"
            v-if="showOptionalFieldsButton"
            v-on:click="setShowOptionalFields"
          >
            <b-icon icon="caret-down-square" aria-hidden="true"></b-icon>
            Advanced
          </b-button>-->

          <!-- Optional Fields. -->
<!--          <b-card bg-varint="light" v-if="showOptionalFields">
            <b-form-group
              v-if="showOptionalFields"
              label="Optional Fields"
              label-cols-lg="3"
              label-size="lg"
              lable-class="font-weight-bold pt-0"
            >
              <b-form-group
                v-for="field in getOptionalFields"
                v-bind:key="field.id"
                v-bind:label="field.label"
                label-cols-sm="3"
                label-align-sm="right"
              >
                <b-form-input
                  v-model="field.content"
                  v-bind:required="field.required"
                ></b-form-input>
                &lt;!&ndash;            <b-form-input
                            v-model="field.content"
                            v-bind:required="field.required"
                          ></b-form-input>&ndash;&gt;
              </b-form-group>
            </b-form-group>
          </b-card>-->

<!--          <b-button type="submit" variant="primary">
            <b-icon icon="bookmark-plus" aria-hidden="true"></b-icon>
            Create new author
          </b-button>-->

        </b-form>
      </b-container>
      <template #modal-footer="{ cancel, ok }">
        <b-button variant="secondary" @click="cancel()">
          Cancel
        </b-button>
        <b-button variant="primary" @click="ok()">
          Create new author
        </b-button>
      </template>
    </b-modal>

    <b-modal ref="successModal" title="Author created" ok-only>You created an new author.</b-modal>

    <ServerComNewAuthor
      ref="com"
      v-on:requestResponse="handleRequestResponse"
      v-on:requestError="handleRequestError"
    ></ServerComNewAuthor>
  </div>
</template>

<script>
import ServerComNewAuthor from "@/components/ServerComNewAuthor";
import newAuthorFormContent from "@/js_files/newAuthorFormContent.js";

export default {
  name: "NewAuthorModal",
  components: {
    ServerComNewAuthor,
  },
  data() {
    return {
      modalTitle: "Create a new author",
      formContent: newAuthorFormContent.allTypes.author,
      showOptionalFields: "",
      newAuthor: "",
      responseText: "",
      success: false,
    };
  },

  methods: {
    showNewAuthorModal: function () {
      // Trigger the modal.
      this.$refs["modal-1"].show();
    },
    wasRequestSuccess: function () {
      // Was the HTTP request a success? Return boolean.
      if (this.requestResponse.readyState === this.requestResponse.DONE) {
        let status = this.requestResponse.status;
        if (status === 0 || (status >= 200 && status < 400)) {
          return true;
        }
      }
      return false;
    },
    createNewAuthor: function () {
      this.newAuthor = [].concat(
        this.getRequiredFields,
        this.getOptionalFields
      );
      this.$refs.com.triggerServerComNewAuthor(this.newAuthor);
    },
    setShowOptionalFields: function () {
      this.showOptionalFields = !this.showOptionalFields;
    },
    resetOptionalFieldsButton: function () {
      this.showOptionalFields = false;
    },
    handleRequestResponse: function (response) {
      this.responseText = response.responseText;
      this.$refs["modal-1"].hide();
      this.success = true;
      this.$emit("newAuthorSuccess");
      this.$refs.successModal.show();
    },
    handleRequestError: function () {
      this.responseText = "error";
    },
  },
  computed: {
    getModalTitle: function () {
      return this.modalTitle;
    },
    getRequiredFields: function () {
      // Get the required fields for the selected type.
      return this.formContent.filter(function (elem) {
        if (elem.required === true) return true;
      });
    },
    getOptionalFields: function () {
      // Get the fields for the form that are only optional.
      return this.formContent.filter(function (elem) {
        if (elem.required === false) return true;
      });
    },
    showOptionalFieldsButton: function () {
      // Are there any optional fields -> Show the button indicating optional fields and make them available.
      if (this.getOptionalFields.length === 0) return false;
      else return true;
    },
    getNewAuthorSuccess: function () {
      return this.success;
    },
  },
};
</script>

<style></style>
