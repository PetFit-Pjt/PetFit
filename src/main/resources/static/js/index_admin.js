document.addEventListener('DOMContentLoaded', function() {
  const gallery = document.querySelector('.gallery');
  const slides = document.querySelectorAll('.slide');
  const slideWidth = slides[0].offsetWidth;
  const totalSlides = slides.length;
  let currentIndex = 0;
  let slideInterval; // 슬라이드 간격을 저장하는 변수

  function nextSlide() {
    currentIndex = (currentIndex + 1) % totalSlides;
    updateSlidePosition();
  }

  function prevSlide() {
    currentIndex = (currentIndex - 1 + totalSlides) % totalSlides;
    updateSlidePosition();
  }

  function updateSlidePosition() {
    const offset = -currentIndex * slideWidth;
    gallery.style.transition = 'transform 0.5s ease'; // 부드러운 전환을 위해 transition 속성 추가
    gallery.style.transform = `translateX(${offset}px)`;
  }

  function startSlideInterval() {
    slideInterval = setInterval(nextSlide, 2000); // 2초마다 슬라이드 변경
  }
  
  function stopSlideInterval() {
    clearInterval(slideInterval);
  }

  // 초기 슬라이드 위치 설정
  updateSlidePosition();

  // 마지막 슬라이드 다음에 첫 번째 슬라이드가 나타나도록 설정
  gallery.appendChild(slides[0].cloneNode(true));
  gallery.appendChild(slides[1].cloneNode(true));
  gallery.appendChild(slides[2].cloneNode(true));

  // 마우스가 슬라이더 위에 있을 때 슬라이드 정지
  gallery.addEventListener('mouseenter', function() {
    stopSlideInterval();
  });

  // 마우스가 슬라이더에서 벗어날 때 슬라이드 재시작
  gallery.addEventListener('mouseleave', function() {
    startSlideInterval();
  });

  // 마지막 슬라이드 다음에 첫 번째 슬라이드가 나타나도록 설정
  gallery.appendChild(slides[0].cloneNode(true));
  gallery.appendChild(slides[1].cloneNode(true));
  gallery.appendChild(slides[2].cloneNode(true));

  // 이전 버튼 클릭 시 이벤트
  document.getElementById('prevButton').addEventListener('click', function() {
    prevSlide();
  });

  // 다음 버튼 클릭 시 이벤트
  document.getElementById('nextButton').addEventListener('click', function() {
    nextSlide();
  });

  // 슬라이드 자동 재생 시작
  startSlideInterval();
});

