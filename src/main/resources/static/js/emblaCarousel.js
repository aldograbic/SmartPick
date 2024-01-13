document.addEventListener("DOMContentLoaded", function () {
  const autoplayOptions = {
    delay: 7000,
  };

  const emblaNode = document.querySelector(".embla");
  const embla = EmblaCarousel(emblaNode, { loop: true }, [
    EmblaCarouselAutoplay(autoplayOptions),
  ]);
});
