<template>
  <div>
    <section id="Profile">
      <Profile :users="users" />
      <Eintraege
        :users="users"
        :posts="posts"
        :jusers="jusers"
        :threads="threads"
      ></Eintraege>
    </section>
   
  </div>
</template>

<script>
import Profile from "@/components/Profile.vue";
import Eintraege from "@/components/Eintraege.vue";
import sourceData from "@/data.json";

export default {
  components: {
    Profile,
    Eintraege,
  },
  methods: {
    postById(postId) {
      return this.posts.find((p) => p.id === postId);
    },
    userById(userId) {
      return this.post.find((u) => u.userId === userId);
    },
  },
  data() {
    return {
      Eintraege,

      jusers: sourceData.users,
      threads: sourceData.threads,
      posts: sourceData.posts,
      users: [
        {
          id: 1,
          name: "Antek",
          age: 21,
          publications: 102,
          followers: 345,
          following: 3564,
          title: "Book1",
          content:
            "orem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore",
          pages: 122,
        },
        {
          name: "Pete",
          age: 21,
          publications: 102,
          followers: 345,
          following: 3564,
          title: "Book2",
          content:
            "orem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore",
          pages: 122,
        },
        {
          name: "Antek",
          age: 21,
          publications: 102,
          followers: 345,
          following: 3564,
          title: "Book3",
          content:
            "orem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore",
          pages: 12234,
        },
        {
          name: "Max",
          age: 21,
          publications: 102,
          followers: 345,
          following: 3564,
          title: "Book4",
          content:
            "orem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore",
          pages: 122,
        },
        {
          name: "Pete",
          age: 21,
          publications: 102,
          followers: 345,
          following: 3564,
          title: "Book2",
          content:
            "orem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore",
          pages: 1223,
        },
        {
          name: "Antek",
          age: 21,
          publications: 102,
          followers: 345,
          following: 3564,
          title: "Book3",
          content:
            "orem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore",
          pages: 142,
        },
        {
          name: "Max",
          age: 21,
          publications: 102,
          followers: 345,
          following: 3564,
          title: "Book4",
          content:
            "orem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore",
          pages: 1232,
        },
        {
          name: "Flioio",
          age: 21,
          publications: 102,
          followers: 345,
          following: 3564,
          title: "Book3",
          content:
            "orem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore",
          pages: 124,
        },
        {
          name: "Max",
          age: 21,
          publications: 102,
          followers: 345,
          following: 3564,
          title: "Book6",
          content:
            "orem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore",
          pages: 1225,
        },
        {
          name: "Flioio",
          age: 21,
          publications: 102,
          followers: 345,
          following: 3564,
          title: "Book7",
          content:
            "orem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore",
          pages: 1226,
        },
      ],
    };
  },
};
</script>


<style>
</style>