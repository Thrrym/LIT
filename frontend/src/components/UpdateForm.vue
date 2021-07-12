<template>
  <div>
    <b-container>
      <!-- Create the form the get the information for the selected type. -->
      <b-form v-on:input="emitNewEntry">
        <b-card bg-varint="light">
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
              <div v-if="isAuthor(field)">
                <!-- Insert existing authors. Can only be removed. No edit -->
                <b-input-group
                  v-for="author in originalAuthors"
                  v-bind:key="author"
                >
                  <b-form-input
                    readonly
                    v-bind:placeholder="author"
                  ></b-form-input>
                  <b-button v-on:click="removeOriginalAuthor(author)">-</b-button>
                </b-input-group>

                <!-- Insert option to add further authors. -->
                <b-input-group v-on:input="selectedAuthors = newAuthors">
                  <b-form-select
                    v-bind:options="authorOptions"
                    v-model="selectedAuthors[0]"
                  ></b-form-select>
                  <b-button v-on:click="addNewAuthor" variant="outline-secondary">
                    Create new author
                  </b-button>
                  <b-button v-on:click="setAdditionalAuthors" v-b-tooltip.hover title="Add more authors to lit entry.">+</b-button>
                </b-input-group>
                <b-form-select
                  v-for="index in additionalAuthors"
                  v-bind:key="index"
                  v-bind:options="authorOptions"
                  v-model="selectedAuthors[index]"
                ></b-form-select>
              </div>

              <b-form-input
                v-else
                v-model="field.content"
                v-bind:required="field.required"
              ></b-form-input>
            </b-form-group>
          </b-form-group>
        </b-card>

        <b-button
          variant="outline-primary"
          v-if="showOptionalFieldsButton"
          v-on:click="setShowOptionalFields"
        >
          <b-icon icon="caret-down-square" aria-hidden="true"></b-icon> Advanced
        </b-button>

        <!-- Optional Fields. -->
        <b-card bg-varint="light" v-if="showOptionalFields">
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
            </b-form-group>
          </b-form-group>
        </b-card>

<!--        <b-button variant="outline-primary" v-on:click="setShowCcField">
          <b-icon icon="caret-down-square" aria-hidden="true"></b-icon> CC
        </b-button>-->

        <!-- CC Field. -->
<!--        <b-card bg-varint="light" v-if="showCcField">
          <b-form-group
            v-if="showCcField"
            label="Send to"
            label-cols-lg="3"
            label-size="lg"
            lable-class="font-weight-bold pt-0"
          >
            <b-form-group label="CC" label-cols-sm="3" label-align-sm="right">
              <b-form-input v-model="ccContent" type="url"></b-form-input>
            </b-form-group>
          </b-form-group>
        </b-card>-->
      </b-form>
    </b-container>
    <NewAuthorModal
      ref="NewAuthorModal"
      v-on:newAuthorSuccess="getAuthorOptions"
    ></NewAuthorModal>
    <GetAuthors
      ref="GetAuthors"
      v-on:getAuthorsSuccess="setAuthorOptions"
    ></GetAuthors>
  </div>
</template>

<script>
import GetAuthors from "@/components/GetAuthors";
import NewAuthorModal from "@/components/NewAuthorModal";

export default {
  name: "UpdateForm",
  components: {
    GetAuthors,
    NewAuthorModal,
  },

  props: {
    formContent: {
      required: true,
      type: Array,
    },
  },

  watch: {
    formContent: function () {
      // Watch prop with content of the form for change.
      // On change: Reset the Button for optional fields.
      this.resetOptionalFieldsButton();
    },
  },

  data() {
    return {
      showOptionalFields: false,
      showCcField: false,
      ccContent: "",
      selectedAuthors: [],
      additionalAuthors: 0,
      authorOptions: [],
      currentAuthors: [],
      newAuthors: [],
      consolidatedAuthors: [],
      originalAuthors: [],
      formResult: [],
    };
  },

  computed: {
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
    getOriginalSelectedAuthors: function () {
      return this.formContent.filter(function (elem) {
        if (elem.name === "authors") return true;
      })[0].content;
    },
    getConsolidatedAuthors: function () {
      return [].concat(this.originalAuthors, this.newAuthors);
    },
    getFormResult: function () {
      let result = [].concat(this.getRequiredFields, this.getOptionalFields);
      result.filter(function(elem) {if (elem.name==="authors") return true;})[0].content = this.getConsolidatedAuthors
      return result
    },
  },

  methods: {
    emitNewEntry: function () {
      // Gather the fields from the form. Required and optional fields.
      // Combine both arrays with fields.
      this.consolidatedAuthors = this.getConsolidatedAuthors;
      /*console.log("consolidatedAuthors", this.consolidatedAuthors);
      console.log("Fuck!!!!!", this.formContent.filter(function (elem) {
        if (elem.name === "authors") return true;
      })[0].content)*/// = this.consolidatedAuthors;
      /*this.formContent.filter(function (elem) {
        if (elem.name === "authors") return true;
      })[0].content = this.getConsolidatedAuthors;*/
      /*console.log(this.getOriginalSelectedAuthors);*/
      this.formResult = this.getFormResult;
      // this.formResult.filter(function(elem) {if (elem.name==="authors") return true;})[0].content = this.getConsolidatedAuthors
      console.log("formResult", this.formResult)
      // Emit the appropiate event to superior component with the fields as content of event.
      this.$emit("cc", this.ccContent);
      this.$emit("entryToBeUpdated", this.formResult);
    },
    setShowOptionalFields: function () {
      this.showOptionalFields = !this.showOptionalFields;
    },
    resetOptionalFieldsButton: function () {
      this.showOptionalFields = false;
    },
    setShowCcField: function () {
      this.showCcField = !this.showCcField;
    },
    isAuthor: function (field) {
      return field.name === "authors";
    },
    getAuthorOptions: function () {
      this.$refs.GetAuthors.triggerGetAuthors();
    },
    setAuthorOptions: function (authorOptions) {
      this.authorOptions = authorOptions;
    },
    addNewAuthor: function () {
      this.$refs.NewAuthorModal.showNewAuthorModal();
    },
    removeOriginalAuthor: function (authorId) {
      this.originalAuthors.pop(authorId);
    },
    setAdditionalAuthors: function () {
      this.getAuthorOptions();
      this.additionalAuthors += 1;
    },
    updateAuthors: function () {
      this.consolidatedAuthors = [].concat(this.originalAuthors, this.newAuthors);
    },
  },

  mounted: function () {
    // On loading of the form, get the existing authors from the server.
    this.getAuthorOptions();
    this.originalAuthors = this.getOriginalSelectedAuthors;
  },
};
</script>

<style></style>
