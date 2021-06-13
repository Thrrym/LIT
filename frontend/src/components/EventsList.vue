<template>
  <div class="events container">
    <h2 class="subtitle is-3">Check out recent publications</h2>
    <div class="columns is-multiline">
      <div v-for="event in events" :event="event" :key="event.id" class="column is-one-quarter">
        <router-link :to="`/event/${event.id}`">
          <EventCard :event="event" />
        </router-link>
      </div>
    </div>
  </div>
</template>
<script>
import EventCard from "@/components/EventCard";
export default {
  name: "EventsList",
  components: {
    EventCard
  },
  data() {
    return {
      event: {},
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
      ]
    };
  }
};
</script>
<style lang="scss" scoped>
.events {
  margin-top: 100px;
  text-align: center;
}
</style>