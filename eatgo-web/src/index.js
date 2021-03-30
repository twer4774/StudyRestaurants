(async () => {
   const url = 'http://localhost:8080/restaurants';
   const response = await fetch(url);
   const restaurants = await response.json();


   const element = document.getElementById('app');
   element.innerHTML = `
        ${restaurants.map(restaurant =>
        `
        <p>
          ${restaurant.id}
          ${restaurant.name}
          ${restaurant.address}
        <p>
        `
        ).join('')} <!--,를 없애기 위해 조인 실행-->
   `;
})();