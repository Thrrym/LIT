<template>
  <div class="main">
    <div v-for="thread in threads" :key="thread.id">
      <div
        class="card"
        style="width: 18rem"
        v-for="postId in thread.posts"
        :key="postId"
      >
        <div class="card-body">
          <h5 class="card-title">{{ thread.title }}</h5>

          <p class="card-text" id="card-text">
            {{ postById(postId).text }}
          </p>
          <a
            href="#"
            class="btn btn-danger"
            id="btn-more"
            @click="showModal = true"
            >More</a
          >
        </div>
      </div>
    </div>
    <transition name="fade" appear>
      <div class="modal-overlay" v-if="showModal" @click="showModal = false">
        <div class="modal-card" id="ccc">
          <div class="card w-50" style="width: 17rem">
            <div class="card-body">
              <div id="list-inBox">
                <ul class="list-group d-inline-flex">
                  <li
                    class="list-group-item d-flex justify-content-between align-items-center"
                  >
                    Author:
                    <span class="badge bg-dark">{{ users[0].name }}</span>
                  </li>
                  <li
                    class="list-group-item d-flex justify-content-between align-items-center"
                  >
                    Pages:
                    <span class="badge bg-dark">{{ users[0].pages }}</span>
                  </li>
                </ul>
              </div>
              <p class="card-text">
                Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed
                diam nonumy eirmod tempor invidunt ut labore et dolore magna
                aliquyam erat, sed diam voluptua. At vero eos et accusam et
                justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea
                takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum
                dolor sit amet, consetetur sadipscing elitr, sed diam nonumy
                eirmod tempor invidunt ut labore et dolore magna aliquyam erat,
                sed diam voluptua. At vero eos et accusam et justo duo dolores
                et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus
                est Lorem ipsum dolor sit amet.
              </p>
            </div>
            <a
              href="#"
              class="btn btn-danger"
              id="button-close"
              @click="showModal = false"
              >Close</a
            >
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>

<script>
export default {
  name: "Eintraege",
  props: {
    users: {
      type: Array,
      required: true,
    },
    threads: {
      type: Array,
      required: true,
    },
    jusers: {
      type: Array,
      required: true,
    },
    posts: {
      type: Array,
      required: true,
    },
  },
  data() {
    return {
      showModal: false,
    };
  },
  methods: {
    postById(postId) {
      return this.posts.find((p) => p.id === postId);
    },
    usersById(userId) {
      return this.users.find((u) => u.id === userId);
    },
  },
};
</script>
<style>
.main {
  font-family: "Monaco", Monospace;
  display: flex;
  align-items: center;
  margin-bottom: 0;
  flex-wrap: wrap;
  padding: 15px;
  margin: 1em;
  justify-content: center;
}
.main .card {
  padding: 0;
  margin: 1em;
  box-shadow: 0 5px 5px rgba(0, 0, 0, 0.15);
  transition: 0.4s ease-out;
}
#card-text {
  text-align: center;
  height: 150px;
  width: 250px;
  overflow: hidden;
}
.main .card .card-body .list {
  text-align: center;
  align-items: center;
  list-style-type: none;
  padding: 0;
}
.main .card .card-body .btn {
  font-family: "Copperplate", Fantasy;
}
.main .card .card-body .fade {
  margin: 100;
  padding: 100;
  box-sizing: border-box;
}
.modal-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 220;
  z-index: 98;
  cursor: pointer;
  background-color: rgba(255, 253, 253, 0.767);
}
#ccc {
  font-family: "Monaco", Monospace;
  display: flex;
  align-items: center;
  margin-bottom: 0;
  flex-wrap: wrap;
  padding: 15px;
  margin: 1em;
  justify-content: center;
}
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.6s;
}

.fade-enter,
.fade-leave-to {
  opacity: 0;
}
#button-close {
  align-items: left;
}
#list-inBox {
  justify-content: left;
}
</style>
