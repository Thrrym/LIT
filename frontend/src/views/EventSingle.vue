<template>
  <div class="event-single">
    <section class="hero is-primary">
      <div class="hero-body">
        <div class="container">
          <h1 class="title">{{ event.name }}</h1>
          <h2 class="subtitle">
            <strong>Date:</strong>
            {{ event.date }}
            <br />
            <strong>Time:</strong>
            {{ event.time }}
          </h2>
        </div>
      </div>
    </section>
    <section class="event-content">
      <div class="container">
        <p class="is-size-4 description">{{ event.description }}</p>
        <p class="is-size-5">
          <strong>Author:</strong>
          {{ event.location }}
        </p>
        <p class="is-size-5">
          <strong>Category:</strong>
          {{ event.category }}
        </p>
        <div class="event-images columns is-multiline has-text-centered">
          <div v-for="image in event.images" :key="image.id" class="column is-one-third">
            <img :src="`${image}`" :alt="`${event.name}`" />
          </div>
        </div>
      </div>
    </section>
  </div>
</template>
<script>
export default {
  name: "Publications",
  data() {
    return {
      events: [
         {
          id: 1,
          name: "Publication 1",
          category: "blank",
          description:
            ".",
          featuredImage: "https://images.maennersache.de/leeres-buch,id=b7b231bd,b=maennersache,w=1100,rm=sk.jpeg",
          images: [
            "https://images.maennersache.de/leeres-buch,id=b7b231bd,b=maennersache,w=1100,rm=sk.jpeg",
            "https://images.maennersache.de/leeres-buch,id=b7b231bd,b=maennersache,w=1100,rm=sk.jpeg",
            "https://images.maennersache.de/leeres-buch,id=b7b231bd,b=maennersache,w=1100,rm=sk.jpeg"
          ],
          author: "Author",
          date: "11-05-2021"
        },
        {
          id: 2,
          name: "Publication 2",
          category: "blank",
          description:
            ".",
          featuredImage: "https://images.maennersache.de/leeres-buch,id=b7b231bd,b=maennersache,w=1100,rm=sk.jpeg",
          images: [
            "https://images.maennersache.de/leeres-buch,id=b7b231bd,b=maennersache,w=1100,rm=sk.jpeg",
          ],
          author: "Author",
          date: "12-25-2019"
        },
         {
          id: 3,
          name: "Publication 3",
          category: "blank",
          description:
            "Spend an elegant night of dinner and dancing with us as we raise money for our new rescue farm.",
          featuredImage: "https://images.maennersache.de/leeres-buch,id=b7b231bd,b=maennersache,w=1100,rm=sk.jpeg",
          images: [
            "https://images.maennersache.de/leeres-buch,id=b7b231bd,b=maennersache,w=1100,rm=sk.jpeg",
            "https://images.maennersache.de/leeres-buch,id=b7b231bd,b=maennersache,w=1100,rm=sk.jpeg",
            "https://images.maennersache.de/leeres-buch,id=b7b231bd,b=maennersache,w=1100,rm=sk.jpeg"
          ],
          author: "Author",
          date: "12-25-2019"
        },
        {
          id: 4,
          name: "Publication 4",
          category: "blank",
          description:
            ".",
          featuredImage: "https://images.maennersache.de/leeres-buch,id=b7b231bd,b=maennersache,w=1100,rm=sk.jpeg",
          images: ["https://images.maennersache.de/leeres-buch,id=b7b231bd,b=maennersache,w=1100,rm=sk.jpeg"],
          auhtor: "Author",
          date: "11-21-2019"
        }
      ],
      event: {}
    };
  },
  created() {
    const ID = Number(this.$route.params.id);
    let event = this.events.find(event => event.id === ID);
    this.event = event;
  }
};
</script>