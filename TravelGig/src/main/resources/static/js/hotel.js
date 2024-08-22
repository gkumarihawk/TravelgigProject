$(document).ready(function(){

    var fromFirstModal = false; //Is the first modal or 2nd is called

    function fetchHotels(searchLocation) {
        $.get("/findHotel/"+searchLocation, function(response, status){
            $("#hotelTbl tr").not(".header").remove();
            $.each(response, function(key, value){
				var hotelId = value.hotelId;
                var hotelName = value.hotelName;
                var checkInDate = $("#checkInDate").val();
                var checkOutDate = $("#checkOutDate").val();
                var noRooms = $("#noRooms").val();
                var noGuests = $("#noGuests").val();
                var roomTypes = value.roomTypes; 
   
			// To calculate the overAll Rating
            calculateAverageOverallRating(value, function(averageRating) {
                var reviewLink;
                if (averageRating !== null && averageRating !== undefined) {
                    reviewLink = "<a href='#' class='review-link' data-toggle='modal' data-target='#reviewsModal'>Average Rating: " + averageRating.toFixed(1) + "</a>";
                } else {
                    reviewLink = "<a href='#' class='review-link' data-toggle='modal' data-target='#reviewsModal'>No reviews available</a>";
                }

                var link = "<a href='#' class='hotel-details-link' data-hotel-name='"+hotelName+"' data-checkin-date='"+checkInDate+"' data-checkout-date='"+checkOutDate+"' data-no-rooms='"+noRooms+"' data-no-guests='"+noGuests+"' data-room-types='"+roomTypes+"' data-hotel-id='"+value.hotelId+"'>" + hotelName + "</a>";

				var questionsLink="<a href='#' class='hotel-questions-link' data-toggle='modal' data-target='#questionModal'>questions</a>";

                $("#hotelTbl").append("<tr data-hotel-id='" + value.hotelId + "'><td>"+link+"</td><td><img height='200' width='200' src='"+value.imageURL+"'/></td><td>"+value.starRating+
                "</td><td>"+reviewLink+"</td><td>"+questionsLink+"</td></tr>");
            });
       	 });
      });
	}

	// search button click(gets all the hotel)
    $("#searchBtn").click(function(){
        var searchLocation = $("#searchLocation").val();
        fetchHotels(searchLocation);
    });

	//The method is finally working to calculate the average overall Rating. (I guess I will have to do something to 
	//make sure the page automatically re-renders based on when the button is clicked)
	function calculateAverageOverallRating(value, callback) {
    	if (value && value.hasOwnProperty('hotelId')) {
        	$.ajax({
            	url: 'http://localhost:8484/hotel/reviews/' + value.hotelId,
            	method: 'GET',
            	success: function(reviews) {
                	if (reviews.length > 0) {
                    	var totalRating = 0;
                    	reviews.forEach(function(review) {
                        	totalRating += review.overallRating;
                    	});
                    	var averageRating = totalRating / reviews.length;
                    	callback(averageRating);
                	} else {
	                    callback(null);
               	 	}
            	},
            	error: function(xhr, status, error) {
                	console.error('Failed to fetch reviews:', error);
                	callback(null);
            	}
        	});
    	} else {
        // What if the value is null or isn't getting hotel Id
        console.error('Invalid value or missing hotelId property');
        // Error purposes
        callback(null);
      }
	}
	
	// Event listener for click on hyperlink to show hotel details(From the Fetch Hotel function)
    $(document).on("click", ".hotel-details-link", function(e){
        e.preventDefault(); // Preventing the default action of the hyperlink
        
        var hotelName = $(this).data("hotel-name");
        var checkInDate = $(this).data("checkin-date");
        var checkOutDate = $(this).data("checkout-date");
        var noRooms = $(this).data("no-rooms");
        var noGuests = $(this).data("no-guests");
        var roomTypes = $(this).data("room-types");
        var hotelId = $(this).data("hotel-id");
        fromFirstModal = false;
        showHotelDetails(hotelName, checkInDate, checkOutDate, noRooms, noGuests, roomTypes, hotelId);
    });
    

    // filter hotels based on star ratings, amenities, and price range(amenities still not working)
    function filterHotels() {
        var searchLocation = $("#searchLocation").val();
        var selectedStarRatings = [];
        $(".star_rating:checked").each(function() {
            selectedStarRatings.push(parseInt($(this).val()));
        });
        var maxPrice = parseInt($("#priceRange").val());
        var selectedAmenities = [];
        $(".hotel_amenity:checked").each(function() {
            selectedAmenities.push($(this).val());
        });
        $.get("/findHotel/"+searchLocation, function(response, status){
            $("#hotelTbl tr").not(".header").remove();
            $.each(response, function(key, value){
                if (selectedStarRatings.includes(value.starRating) && value.averagePrice <= maxPrice && containsAllAmenities(value.amenities, selectedAmenities)) {
                    $("#hotelTbl").append("<tr><td>"+value.hotelName+"</td><td><img height='200' width='200' src='"+value.imageURL+"'/></td><td>"+value.starRating+"</td></tr>");
                }
            });
        });
    }

    // Check if a hotel contains all selected amenities(I †hink this is the part where I messed up. I should a few alerts
    //He said not to worry about amenities.)
    function containsAllAmenities(hotelAmenities, selectedAmenities) {
        return selectedAmenities.every(function(selectedAmenity) {
            return hotelAmenities.some(function(hotelAmenity) {
                return hotelAmenity.name === selectedAmenity;
            });
        });
    }
    
    // Event listener for filter button click
    $("#filterBtn").click(function(){
        filterHotels();
    });
    
    $("form").submit(function(event) {
        event.preventDefault();
        // Serialize the form data
        var formData = $(this).serialize();

        // Post request to the form
        $.post("/signup", formData, function(response) {
            // Check if the value is coming
            console.log(response);
        });
    });
    
    // If edit button is clicked I just reopen the 1st modal
	$(document).on("click", ".btn-edit", function() {
    	console.log("Edit button clicked");
    	$("#bookingHotelRoomModal").modal("hide");
    	$("#myModal").modal("show");
	});

    
    function showHotelDetails(hotelName, checkInDate, checkOutDate, noRooms, noGuests, roomTypes, hotelId) {
        $("#modal_hotelName").val(hotelName);
        $("#modal_checkInDate").val(checkInDate);
        $("#modal_checkOutDate").val(checkOutDate);
        $("#modal_noRooms").val(noRooms);
        $("#modal_noGuests").val(noGuests);
        $("#modal_hotelId").val(hotelId);
        //$("#booking_customerMobile").val(customerMobile);
        
        // room types dropdown
        populateRoomTypesDropdown(hotelId);
        
        // AJAX call to fetch discount and averagePrice
        $.ajax({
            url: "/hotel/findHotel/" + hotelId,
            type: "GET",
            success: function(response) {
                var hotel = response; //hotel sounds better that response for the project
                // Updating the fields with discount and averagePrice
                $("#booking_discount").text(hotel.discount);
                $("#booking_price").text(hotel.averagePrice);
                
                if (fromFirstModal) {
                    // Prepopulating fields in the second modal
                    $("#booking_hotelName").val(hotelName);
                    $("#booking_noGuests").val(noGuests);
                    $("#booking_noRooms").val(noRooms);
                    $("#booking_checkInDate").val(checkInDate);
                    $("#booking_checkOutDate").val(checkOutDate);
                    $("#booking_roomType").val(roomTypes);
                    
                    $("#bookingHotelRoomModal").modal("show"); //the second modal if called from the first modal
                } else {
                    $("#myModal").modal("show"); // Show the first modal otherwise
                }
            },
            error: function(xhr, status, error) {
                console.error("Failed to fetch hotel details:", error);
            }
        });
    }
    

    function populateRoomTypesDropdown(hotelId) {
        $.ajax({
            url: "/hotelRoom/roomTypes/" + hotelId,
            type: "GET",
            success: function(data) {
                // Emptying it
                $("#select_roomTypes").empty();
                
                // Populating the dropdown with room types
                data.forEach(function(roomType) {
                    $("#select_roomTypes").append("<option value='" + roomType + "'>" + roomType + "</option>");
                });
            },
            error: function(xhr, status, error) {
                console.error("Failed to fetch room types:", error);
            }
        });
    }
    
    // Event listener for search button click in the first modal
    $(".btn-searchHotelRooms").click(function(){
        fromFirstModal = true;
        var hotelName = $("#modal_hotelName").val();
        var checkInDate = $("#modal_checkInDate").val();
        var checkOutDate = $("#modal_checkOutDate").val();
        var noRooms = $("#modal_noRooms").val();
        var noGuests = $("#modal_noGuests").val();
        var roomTypes = $("#select_roomTypes").val();
        var hotelId = $("#modal_hotelId").val();
        showHotelDetails(hotelName, checkInDate, checkOutDate, noRooms, noGuests, roomTypes, hotelId);
    });

    
       var currentGuest = 1; // Tracking the current guest number

    // Event listener for click on confirm booking button(2nd nodal)
    $(document).on("click", ".btn-confirm-booking", function() {
        
        saveBooking();
        
        $("#bookingHotelRoomModal").modal("hide");

        // Showing the third modal
        $("#guestInfoModal").modal("show");

        // Updating the modal title for the first guest
        $("#guestInfoModal .modal-title").text(currentGuest + ordinalSuffix(currentGuest) + " Guest Information:");

        // Increment the current guest number for the next guest
        currentGuest++;
    });
    
    //If the person isn't logged in redirecting to the login link 
    $(document).on("click", ".btn-notConfirm-booking", function(){
		alert("Please log in");
		
		window.location.href = "/login";
	})
	
	// Event listener for clicking on save guest info button
	$(document).on("click", "#saveGuestInfo", function() {
    // Getting all the guests info
    	var firstName = $("#firstName").val();
    	var lastName = $("#lastName").val();
    	var age = $("#age").val();
    	var gender = $("#gender").val();
    	var phone = $("#booking_customerMobile").val();
    	
    	console.log("First Name:", firstName);
    	console.log("Last Name:", lastName);
    	console.log("Age:", age);
    	console.log("Gender:", gender);
    	console.log("Phone:", phone);

    	// guest object
   	 	var guest = {
        	"firstName": firstName,
        	"lastName": lastName,
       	 	"age": age,
        	"gender": gender,
        	"phoneNumber": phone
    };

    // Sending the guest object to the server to save
    $.ajax({
        type: "POST",
        url: "http://localhost:8484/saveGuest",
        contentType: "application/json",
        data: JSON.stringify(guest),
        success: function(response) {
			//alert("Hey your work is working");
            console.log("Guest saved successfully:", response);
        },
        error: function(xhr, status, error) {
            console.error("Failed to save guest:", error);
        }
    });

	//Depending the number of guests enter it should show that many times this input fields
    // Clearing the input fields
    $("#guestInfoModal input[type='text']").val("");
    $("#guestInfoModal input[type='number']").val("");
    $("#guestInfoModal select").val("male"); // Reseting gender to male

    // Updating the modal title for the next guest
    $("#guestInfoModal .modal-title").text(currentGuest + ordinalSuffix(currentGuest) + " Guest Information:");

	var numGuests = parseInt($("#modal_noGuests").val());
    // After all the guests information has been entered (closeing the modal and reset the current guest number)
    if (currentGuest > numGuests) {
        $("#guestInfoModal").modal("hide");
        // But what if the same user makes another booking (start from 1)
        currentGuest = 1;
    } else {
        // Incrementing the current guest number for the next guest
        currentGuest++;
    }
});

    // Function to add ordinal suffix to numbers
    function ordinalSuffix(number) {
        var suffixes = ["th", "st", "nd", "rd"];
        var v = number % 100;
        return suffixes[(v - 20) % 10] || suffixes[v] || suffixes[0];
    }


	// Make AJAX call to get hotelRoomId based on roomType
	function getHotelRoomId(roomType, onSuccess, onError) {
    	$.ajax({
        	url: "http://localhost:8383/hotelRoom/getHotelRoomId",
        	type: "GET",
        	data: { roomType: roomType },
        	success: function(hotelRoomId) {
            	onSuccess(hotelRoomId);
        	},
        error: function(xhr, status, error) {
            console.error("Failed to fetch hotelRoomId:", error);
            onError(error);
        }
    });
}

 	//Fetch user email by username
	function getUserEmailByUsername(username, onSuccess, onError) {
    	$.ajax({
       		url: "/user/email/" + username,
        	type: "GET",
        	success: function(email) {
            	onSuccess(email);
       		},
        	error: function(xhr, status, error) {
            	console.error("Failed to fetch user email:", error);
            	onError(error);
       		}
    	});
	}

// Define EmailService object(Not gonna delete what if in future need to send plain email without pdf)
/*var EmailService = {
    sendNotificationEmail: function(email, subject, content) {
        // Simulate sending email by logging to console
        console.log("Sending email to:", email);
        console.log("Subject:", subject);
        console.log("Content:", content);
        console.log("Email sent successfully!");
    }
};*/

	// Sending the PDF email
	function sendPDFEmail(userEmail, hotelId, checkInDate, checkOutDate, finalCharges) {
    // Prepare booking details string
    	var bookingDetails = "Hotel Id: " + hotelId + "\n" +
                         "Check-In Date: " + checkInDate + "\n" +
                         "Check-Out Date: " + checkOutDate + "\n" +
                         "Final Charges: $" + finalCharges; 

    	$.ajax({
        	url: "/generate-and-send-pdf",
        	type: "GET",
        	data: {
            	userEmail: userEmail,
            	bookingDetails: bookingDetails
        	},
        	success: function(response) {
            	console.log("PDF email sent successfully");
        	},
        	error: function(xhr, status, error) {
            	console.error("Failed to send PDF email:", error);
        	}
    	});
	}

	var finalCharges = 320;
	// Function to save booking
	function saveBooking() {
    	// Getting hotelId from the modal(1st modal)
    	let hotelId = $('#modal_hotelId').val();

    	// Get roomType from the modal(2nd)
    	let roomType = $('#booking_roomType').val();

    	//function to get hotelRoomId
    	getHotelRoomId(roomType, function(hotelRoomId) {
        // Now that I have hotelRoomId (data retrieval)
        	let customerMobile = $('#booking_customerMobile').val();

        	// (From second modal)
        	let noRooms = $('#modal_noRooms').val();
        	let checkInDate = $('#modal_checkInDate').val();
        	let checkOutDate = $('#modal_checkOutDate').val();
        	let price = parseFloat($('#booking_price').text());
        	let discount = parseFloat($('#booking_discount').text());
        	var status = (new Date(checkInDate) < Date.now()) ? 'COMPLETED' : 'UPCOMING';

        	// (the logged in user)(Hidden field in line 38(home.jsp))
        	let userName = $('#logged-in-user-name').val();
			
        	// Fetching the user email based on username
        	getUserEmailByUsername(userName, function(userEmail) {
				//Constructing the data object
            	const data = {
                	hotelId: hotelId,
                	hotelRoomId: hotelRoomId,
                	noRooms: noRooms,
                	checkInDate: checkInDate,
                	checkOutDate: checkOutDate,
                	bookedOnDate: Date.now(),
                	status: status,
                	price: price,
                	roomType: roomType,
                	discount: discount,
                	customerMobile: customerMobile,
                	userName: userName,
                	userEmail: userEmail,
                	taxRateInPercent: 5,
                	finalCharges: price - discount + (0.05 * price),
                	totalSavings: discount
            	};

            	// Making AJAX call to save booking with data
            	$.ajax({
                	url: "http://localhost:8484/saveBooking",
                	type: "POST",
                	contentType: "application/json",
                	data: JSON.stringify(data),
                	success: function(booking) {
                    	console.log("Booking saved successfully:", booking);
                    	
                    	//sendEmail(userEmail, "Booking Confirmation", "Your booking has been confirmed. Thank you for choosing our service!");
                    	sendPDFEmail(userEmail, hotelId, checkInDate, checkOutDate, finalCharges);
                    	// Send notification email
                    //EmailService.sendNotificationEmail(userEmail, "Booking Confirmation", "Your booking has been confirmed. Thank you for choosing our service!");
                    	alert('Booking saved successfully!');
                	},
                	error: function(xhr, status, error) {
                    	console.error("Failed to save booking:", error);
                    	alert('Error occurred while saving booking');
                	}
            	});
        	}, function(error) {
            	 alert("Unable to get the email");
        	});
    	},
    	function(error) {

    	});
    	
    	
	}
	
    
    //alert($('#logged-in-user-name').val());
    let username = $('#logged-in-user-name').val();
    //alert("username: " + username);
    
    
    $('#viewBookingsBtn').click(function() {

    let username = $('#logged-in-user-name').val();

    // Making AJAX request to fetch bookings for the user
    $.ajax({
        url: 'http://localhost:8484/findBookingByUserName/' + username,
        type: 'GET',
        success: function(data) {
            // Clear previous bookings
            $('#booking-table-body').empty();

            // Iterating over each booking of the user and add it to the table body
            data.forEach(function(booking) {
                var actionHtml = '';
                if (booking.status === 'UPCOMING') {
                    actionHtml = '<button class="btn-cancel-booking" data-booking-id="' + booking.bookingId + '">Cancel</button>';
                } else if(booking.status === 'COMPLETED'){
                    actionHtml = '<button class="btn-review-please" data-booking-id="' + booking.bookingId + '">Review Please</button>';
                }
                var bookingHtml = '<tr>';
                bookingHtml += '<td>' + booking.bookingId + '</td>';
                bookingHtml += '<td>' + booking.bookedOnDate + '</td>';
                bookingHtml += '<td>' + booking.checkInDate + '</td>';
                bookingHtml += '<td>' + booking.checkOutDate + '</td>';
                bookingHtml += '<td>' + booking.customerMobile + '</td>';
                bookingHtml += '<td>' + booking.price + '</td>';
                bookingHtml += '<td>' + booking.status + '</td>';
                bookingHtml += '<td>' + actionHtml + '</td>';
                bookingHtml += '</tr>';
                $('#booking-table-body').append(bookingHtml);
            });

            // Handling the cancel button in the Recent bookings
            $('.btn-cancel-booking').click(function() {
                var bookingId = $(this).data('booking-id');
                // Making AJAX request to update the status to CANCELLED
                $.ajax({
                    url: 'http://localhost:8484/updateBookingStatus/' + bookingId,
                    type: 'PUT',
                    contentType: 'application/json',
                    data: JSON.stringify({ status: 'CANCELLED' }),
                    success: function(response) {
                        console.log('Booking status updated to CANCELLED:', response);
                        // Remove the cancel button if the button has been clicked
                        $(this).remove();
                    },
                    error: function(xhr, status, error) {
                        console.error('Error updating booking status:', error);
                    }
                });
            });
            
            
            
           // WHen clicked on the rebiew button
            $('.btn-review-please').click(function() {
				//alert($(this).data('booking-id'));
				var bookingId = $(this).data('booking-id');
    
    			// Seting the booking ID in the review modal form
    			$('#bookingIdInput').val(bookingId);
				
                $('#reviewModal').modal('show');
            }); 
            
            
        },
        error: function(xhr, status, error) {
            console.error('Error fetching bookings:', error);
        }
    });
});

	//var bookingId = $(this).data('booking-id');

	$('#review-submit').click(function() {
		
    // Getting the review data from the modal fields
    var serviceRating = parseFloat($('#serviceRating').val());
    var amenitiesRating = parseFloat($('#amenitiesRating').val());
    var bookingProcessRating = parseFloat($('#bookingProcessRating').val());
    var wholeExpRating = parseFloat($('#wholeExpRating').val());
    var reviewText = $('#review-text').val();
    //var overallRating = $('#overallRating').val(); 
	var bookingId = $('#bookingIdInput').val();
    alert(bookingId);
    var overallRating = (serviceRating + amenitiesRating + bookingProcessRating + wholeExpRating) / 4;

	
    // Constructing the review object
    var review = {
        //bookingId: bookingId,
        serviceRating: serviceRating,
        amenitiesRating: amenitiesRating,
        bookingProcessRating: bookingProcessRating,
        wholeExpRating: wholeExpRating,
        text: reviewText,
        overallRating: parseFloat(overallRating.toFixed(1)),
        booking: { bookingId: bookingId }
    };

    // Making an AJAX request to save the review
    $.ajax({
        url: 'http://localhost:8484/saveReview',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(review),
        success: function(response) {
            console.log('Review saved successfully:', response);

            $('#reviewModal').modal('hide');
            $('#successModal').modal('show');
        },
        error: function(xhr, status, error) {
            console.error('Error saving review:', error);

        }
    });
});

	// Function to load reviews for a specific hotel
    function loadReviews(hotelId) {
        // Making AJAX request to fetch reviews for the specified hotelId
        $.ajax({
            url: 'http://localhost:8484/hotel/reviews/' + hotelId,
            method: 'GET',
            success: function(response) {
                // Populating the reviews modal with the received data
                var reviewsHtml = '<ul>';
                response.forEach(function(review) {
                    reviewsHtml += '<li>';
                    reviewsHtml += '<strong>Comment:</strong> ' + review.text + '<br>';
                    reviewsHtml += '<strong>Service Rating:</strong> ' + review.serviceRating + '<br>';
                    reviewsHtml += '<strong>Amenities Rating:</strong> ' + review.amenitiesRating + '<br>';
                    reviewsHtml += '<strong>Booking Process Rating:</strong> ' + review.bookingProcessRating + '<br>';
                    reviewsHtml += '<strong>Whole Experience Rating:</strong> ' + review.wholeExpRating + '<br>';
                    reviewsHtml += '<strong>Overall Rating:</strong> ' + review.overallRating + '<br>';
                    reviewsHtml += '</li>';
                });
                reviewsHtml += '</ul>';
                $('#reviews-list').html(reviewsHtml);
            },
            error: function(xhr, status, error) {
                console.error('Error fetching reviews:', error);
            }
        });
    }

    // Event handler for clicking on the "Reviews" link
    $(document).on('click', '.review-link', function(event) {
        event.preventDefault();

        // Getting the hotel ID from the data attribute(It is already in the tr)
        var hotelId = $(this).closest('tr').data('hotel-id');
		alert("Hotel Id: "+ hotelId);
        loadReviews(hotelId);

        // Opening the reviews modal
        $('#reviewsModal').modal('show');
    });
    
    $(document).on("click", ".btn-notSubmit-question", function(){
		alert("Please log in");
		
		window.location.href = "/login";
	})
	
	
	// Event listener for clicking on the "questions" link
    $(document).on('click', '.hotel-questions-link', function(event) {
        event.preventDefault(); 

        // Getting the hotel ID from the data attribute
        var hotelId = $(this).closest('tr').data('hotel-id');

        // Setting the hotelId value in the hidden input field
        $('#hotelId').val(hotelId);

        // Opening the question modal
        $('#questionModal').modal('show');
    });

    // Submitting question form
    $('#questionForm').submit(function(event) {
        event.preventDefault();

        // Retrieving the hotel ID from the hidden input field(Coming from the question Modal)
        var hotelId = $('#hotelId').val();

        // Getting the question data from the form
        var question = $('#question').val();

        let username = $('#logged-in-user-name').val();

        // Making the AJAX request to save the question
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8383/questionnaires/save', 
            contentType: 'application/json',
            data: JSON.stringify({
                hotelId: hotelId,
                userName: username,
                question: question,
                dateAsked: Date.now()
            }),
            success: function(response) {
                console.log('Question submitted successfully:', response);

                $('#questionModal').modal('hide');
                alert('Question submitted successfully!');
            },
            error: function(xhr, status, error) {
                console.error('Error submitting question:', error);

            }
        });
    });
    
// Event listener for the "View Unanswered Questions" button inside the dropdown
    $('#viewUnansweredQuestionsBtn').click(function() {
        // Opening the recent questions modal(hotel.js)
        $('#recentQuestionsModal').modal('show');
        fetchAllQuestions();
    });
    
function fetchAllQuestions() {
    $.get("http://localhost:8383/allQuestions", function(response, status) {

        $('#allQuestionsList').empty();
        
        response.forEach(function(question) {
            // Generating the HTML for each question
            var questionHtml = '<div class="question">';
            questionHtml += '<p><strong>Hotel ID:</strong> ' + question.hotelId + '</p>';
            questionHtml += '<p><strong>Username:</strong> ' + question.userName + '</p>';
            questionHtml += '<p><strong>Question:</strong> ' + question.question + '</p>';
            questionHtml += '<p><strong>Date Asked:</strong> ' + new Date(question.dateAsked).toLocaleString() + '</p>';
            
            // Checking if the question has an answer
            if (!question.adminAnswer || question.adminAnswer === 'null') {
                // Input box for the admin to answer after every question
                questionHtml += '<div class="form-group">';
                questionHtml += '<label for="answer_' + question.id + '">Answer:</label>';
                questionHtml += '<input type="text" class="form-control" id="answer_' + question.id + '" placeholder="Enter your answer">';
                questionHtml += '</div>';
                
                // After every question that has not been answer will be a submit button
                questionHtml += '<button type="button" class="btn btn-primary submitAnswerBtn">Submit Answer</button>';
            } else {
                // Display the admin answer here
                questionHtml += '<p><strong>Admin Answer:</strong> ' + question.adminAnswer + '</p>';
            }
            
            questionHtml += '</div>';
            
            // Lets add the question to the list
            $('#allQuestionsList').append(questionHtml);
        });
        
        // SUbmit button when the admin answers the question 
        $('.submitAnswerBtn').click(function() {
            var questionId = $(this).prev('.form-group').find('input[type="text"]').attr('id').split('_')[1];
            var answer = $(this).prev('.form-group').find('input[type="text"]').val();
            
            // Sending the updated answer to the server here
            $.ajax({
                url: 'http://localhost:8383/updateAnswer/' + questionId,
                type: 'PUT',
                contentType: 'application/json',
                data: answer,
                success: function(response) {
                    console.log('Answer updated successfully');
                },
                error: function(xhr, status, error) {
                    console.error('Error updating answer:', error);
                }
            });
        });
    });
}


// Event listener for the "View Answered Questions" button inside the dropdown
$('#viewAnsweredQuestionsBtn').click(function() {
    // It will open the modal for the q/a
    $('#answeredQuestionsModal').modal('show');
    
    // UserName of the logged in user
    var username = $('#logged-in-user-name').val();
    
    $.ajax({
        url: 'http://localhost:8383/user/questionnaires/' + username,
        type: 'GET',
        success: function(questions) {
            $('#answeredQuestionsList').empty();            
            questions.forEach(function(question) {
                // Generating the HTML for each question
                var questionHtml = '<div class="question">';
                questionHtml += '<p><strong>Question:</strong> ' + question.question + '</p>';
                questionHtml += '<p><strong>Answer:</strong> ' + question.adminAnswer + '</p>';
                questionHtml += '<p><strong>Hotel ID:</strong> ' + question.hotelId + '</p>';
                questionHtml += '<p><strong>Date Asked:</strong> ' + new Date(question.dateAsked).toLocaleString() + '</p>';
                questionHtml += '</div>';
                
                
                // Appending the HTML to the list of answered questions
                $('#answeredQuestionsList').append(questionHtml);
            });
        },
        error: function(xhr, status, error) {
            console.error('Error fetching answered questions:', error);

        }
    });
});





});
    

