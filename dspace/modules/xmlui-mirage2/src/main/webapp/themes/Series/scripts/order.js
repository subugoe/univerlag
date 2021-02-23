(function($)
{
//Make order dependent on location

function setShoppingcart() {
        $(".asin").hide();
      	$("span.access.order").show();
        $("div.shopping-cart").show();
}

function setAmazon() {
        $("span.access.order").hide();
        $(".asin").show();
}


function ipLookUp() {
 $.ajax({
        url: 'https://api.ip.sb/geoip',
        dataType: 'jsonp',
        jsonp: 'callback',
    })
  .then(
      function success(response) {
         //console.log(response.ip, response.country, response.timezone);
         //console.log(response.continent_code);
	 if (response.continent_code != "EU") {
                $("div.shopping-cart").hide();
                 if ($(".asin").length ) {
                        //console.log("Amazon");
                        setAmazon();
                }
         }
         else 
         {
                console.log("Shopping Chart");
	        setShoppingcart();
         }
      },

      function fail(data, status) {
          console.log('Location request failed.  Returned status of',
                      status);
          setShoppingcart();
      }
  );
}


  // Check location only for not-staff
  if ( !$("#user-dropdown-toggle").length) {
        ipLookUp();
  }



})(jQuery);
