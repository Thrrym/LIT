<template>
  <div>
    <ul class="list-group d-inline-flex">
      <li
        class="list-group-item d-flex justify-content-between align-items-center"
      >
        Username:
        <span class="badge bg-dark rounded-pill">{{ users[0].name }}</span>
      </li>
      <li
        class="list-group-item d-flex justify-content-between align-items-center"
      >
        Publications:
        <span class="badge bg-danger rounded-pill">{{
          users[0].publications
        }}</span>
      </li>
      <li
        class="list-group-item d-flex justify-content-between align-items-center"
      >
        Followers:
        <span class="badge bg-danger rounded-pill">{{
          users[0].followers
        }}</span>
      </li>
      <li
        class="list-group-item d-flex justify-content-between align-items-center"
      >
        Following:
        <span class="badge bg-danger rounded-pill">{{
          users[0].following
        }}</span>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "Profile",
  props: {
    users: {
      type: Array,
      required: true,
    },
  },
};
</script>

<style>
.list-group{
  font-family: "Monaco", Monospace;
  display: flex;
  align-items: stretch;
  margin-bottom: 0;
  flex-wrap: wrap;
  padding: 15px;
  margin: 1em;
  justify-content: center;
  width: 30%;
  margin-top: 2em;
  font-family: "Monaco", Monospace;}
</style>