<template>
  <div>
    <TypeSelector
        :entryTypes="entryTypes"
        v-on:selectedType="setSelectedType"
    >
    </TypeSelector>
  </div>
</template>

<script>
import TypeSelector from "@/components/TypeSelector.vue"

export default {
    name: "NewEntry",
    components: {
        TypeSelector,
    },
    data() {
        return {
            selectedType: "",
            entryTypes: [
                {type: "article"},
                {type: "book"},
                {type: "incollection"},
            ],
            formContent: {
                article: {
                    author: "",
                },
            },

        }
    },
    methods: {
        setSelectedType: function(type) {
            // Type as selected by the selector. To be set as variable in the NewEntry component.
            this.selectedType = type;
        }
    }
}
</script>
<style>
</style>