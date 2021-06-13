<template>
<div>
    <form>
        <input type="text" placeholder="Search...">
    </form>
    <div id="results">
        <SearchResult></SearchResult>
    </div>
</div>
</template>

<script>
import SearchResult from "@/components/SearchResult.vue";

export default {
    name: "Search",
    components: {
        SearchResult,
    },
}
</script>

<style>
</style>