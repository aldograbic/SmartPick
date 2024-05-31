document.addEventListener("DOMContentLoaded", () => {
  const emblaNodes = document.querySelectorAll(".embla");

  emblaNodes.forEach((emblaNode) => {
    const viewportNode = emblaNode.querySelector(".embla__viewport");
    const embla = EmblaCarousel(viewportNode, {
      loop: false,
      align: "start",
    });

    EmblaCarouselAutoplay(embla, {
      delay: 4000,
      stopOnInteraction: true,
    });
  });
});
