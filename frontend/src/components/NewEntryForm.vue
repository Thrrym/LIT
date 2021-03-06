<template>
  <div>
    <b-container>
      <!-- Create the form the get the information for the selected type. -->
      <b-form v-if="showForm" v-on:submit="emitNewEntry">
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
              <b-form-checkbox
                v-if="isNotTextInput(field)"
                v-model="field.content"
                v-bind:required="field.required"
                value="true"
                unchecked-value="false"
              >
              </b-form-checkbox>

              <div v-if="isAuthor(field)">
                <b-input-group v-on:input="field.content = selectedAuthors">
                  <b-form-select
                    v-bind:options="authorOptions"
                    v-model="selectedAuthors[0]"
                  ></b-form-select>
                  <b-button v-on:click="addNewAuthor" variant="outline-secondary"
                    >Create new author</b-button
                  >
                  <!--              <b-button v-on:click="getAuthorOptions">Get the authors</b-button>-->
                  <b-button v-on:click="setAdditionalAuthors" v-b-tooltip.hover title="Add more authors to lit entry.">+</b-button>
                </b-input-group>
                <b-form-select
                  v-for="index in additionalAuthors"
                  v-bind:key="index"
                  v-bind:options="authorOptions"
                  v-model="selectedAuthors[index]"
                ></b-form-select>
              </div>
              <div v-else-if="isAbstract(field)">
                <b-form-textarea v-model="field.content" v-bind:required="field.required"></b-form-textarea>
              </div>
              <b-form-input
                v-else
                v-model="field.content"
                v-bind:required="field.required"
                v-bind:type="getFieldTypeToVerify(field)"
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
              <b-form-checkbox
                v-if="isNotTextInput(field)"
                v-model="field.content"
                v-bind:required="field.required"
                value="true"
                unchecked-value="false"
              >
              </b-form-checkbox>
              <div v-else-if="isAbstract(field)">
                <b-form-textarea v-model="field.content" v-bind:required="field.required"></b-form-textarea>
              </div>
              <b-form-input
                v-else
                v-model="field.content"
                v-bind:required="field.required"
                v-bind:type="getFieldTypeToVerify(field)"
              ></b-form-input>
              <!--            <b-form-input
              v-model="field.content"
              v-bind:required="field.required"
            ></b-form-input>-->
            </b-form-group>
          </b-form-group>
        </b-card>

        <b-button variant="outline-primary" v-on:click="setShowCcField">
          <b-icon icon="caret-down-square" aria-hidden="true"></b-icon> CC
        </b-button>

        <!-- CC Field. -->
        <b-card bg-varint="light" v-if="showCcField">
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
        </b-card>

        <!-- Submit Buttons -->
        <b-button type="submit" variant="primary" v-if="isNotUpdate">
          <b-icon icon="bookmark-plus" aria-hidden="true"></b-icon>
          Create new entry
        </b-button>
        <!-- Submit Buttons -->
        <b-button type="submit" variant="primary" v-else>
          <b-icon icon="bookmark-plus" aria-hidden="true"></b-icon>
          Update entry
        </b-button>
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
import NewAuthorModal from "@/components/NewAuthorModal";
import GetAuthors from "@/components/GetAuthors";
export default {
  name: "NewEntryForm",
  components: {
    NewAuthorModal,
    GetAuthors,
  },

  props: {
    formContent: {
      required: true,
    },
    showForm: {
      type: Boolean,
      required: true,
    },
    update: {
      type: Boolean,
      required: true,
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
      authorOptions: [],
      selectedAuthors: [],
      additionalAuthors: 0,
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
    getFilteredAuthorOptions: function () {
      const component = this;
      return this.authorOptions.filter(function (elem) {
        if (!component.selectedAuthors.includes(elem.value)) return true;
      });
    },
    showOptionalFieldsButton: function () {
      // Are there any optional fields -> Show the button indicating optional fields and make them available.
      if (this.getOptionalFields.length === 0) return false;
      else return true;
    },
    isNotUpdate: function () {
      return !this.update;
    },
  },

  methods: {
    emitNewEntry: function () {
      // Gather the fields from the form. Required and optional fields.
      // Combine both arrays with fields.
      let formResult = [].concat(
        this.getRequiredFields,
        this.getOptionalFields
      );
      // Emit the appropiate event to superior component with the fields as content of event.
      this.$emit("cc", this.ccContent);
      this.$emit("entryToBeCreated", formResult);
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
    isNotTextInput: function (field) {
      if (!Object.prototype.hasOwnProperty.call(field, "inputType")) {
        return false;
      } else if (field.inputType === "checkbox") {
        return true;
      } else {
        return false;
      }
    },
    isAuthor: function (field) {
      return field.name === "authors";
    },
    addNewAuthor: function () {
      this.$refs.NewAuthorModal.showNewAuthorModal();
    },
    getFieldTypeToVerify: function (field) {
      if (Object.prototype.hasOwnProperty.call(field, "inputVerify")) {
        return field["inputVerify"];
      } else {
        return "text";
      }
    },
    getAuthorOptions: function () {
      this.$refs.GetAuthors.triggerGetAuthors();
    },
    setAuthorOptions: function (authorOptions) {
      this.authorOptions = authorOptions;
    },
    setAdditionalAuthors: function () {
      this.getAuthorOptions();
      this.additionalAuthors += 1;
    },
    isAbstract: function (field) {
      return field.name.includes("Abstract");
    },
  },
  mounted: function () {
    // On loading of the form, get the existing authors from the server.
    this.getAuthorOptions();
  },
};
</script>

<style></style>
