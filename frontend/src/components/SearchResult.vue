<template>
<div id="result_box">
  <div id="result_red"></div>
  <div id="result_data">
    <p id="result_title">{{ title }}</p>
    <p id="result_publisher">{{ publisher }}</p>
    <p id="result_info">{{ info }}</p>
  </div>
</div>
</template>

<script>
export default {
  name: "SearchResult",
  data () {
    return {
      title: "Title",
      publisher: "Publisher",
      info: "Info",
    }
  },
}
</script>

<style>
#result_box {
  background-color: rgb(240, 240, 240);
  width: 100%;
  height: 5em;
  margin: 0em;
  padding: 0em;
  position: relative;
}

#result_red {
  background-color: red;
  width: .6em;
  height: 100%;
  margin: 0em;
  padding: 0em;
}

#result_data {
  width: 96%;
  height: 100%;
  position: absolute;
  left: 1.5em;
  top: .4em;
}

p {
  margin-top: 0em;
  margin-bottom: 0em;
  padding: 0em;
  z-index: 1;
}

#result_title {
  font-size: 18px;
  float:left;
  margin-right: .5em;
}

#result_publisher::before {
  content: "by ";
  font-size: 10px;
}

#result_publisher {
  font-size: 10px;
  line-height: 28px;
}

#result_info {
  left: 1.5em;
}
</style>