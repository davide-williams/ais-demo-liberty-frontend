/*
* Copyright IBM Corp. 1987, 2020
* 
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements.  See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership.  The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License.  You may obtain a copy of the License at
* 
* http://www.apache.org/licenses/LICENSE-2.0
* 
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
* 
**/

$(document).ready(
		function() {
			hide_panels();

			// process the form
			$('form').submit(
					function(event) {
						// stop the form from submitting the normal way and
						// refreshing the page
						event.preventDefault();
						hide_panels();
						if ($("#use-odm").is(':checked')) {
							useODM = true
						} else {
							useODM = false
						}
						if ($("#trace").is(':checked')) {
							showTrace = true
						} else {
							showTrace = false
						}
						stateVal = $('#state').find('option:selected').val();
						marriedVal = $('#married').find('option:selected').val();
						var formData = {
							'name' : $('input[name=name]').val(),
							'last-name' :$('input[name=lname]').val(),
							'street' :$('input[name=street]').val(),
							'city' :$('input[name=city]').val(),
							'state' : stateVal,
							'zipcode' :parseInt($('input[name=zip]').val()),
							'ssn' :$('input[name=ssn]').val(),
							'age' :parseInt($('input[name=age]').val()),
							'married': marriedVal,
							'server' : $('input[name=server]').val(),
							'mq-server':$('input[name=mq-server]').val(),
							'mq-port': parseInt($('input[name=mq-port]').val()),
							'mq-qmgr':$('input[name=qmgr]').val(),
							'ruleset' : $('input[name=ruleset]').val(),
							'instanceType' : $('input[name=instanceType]:checked', '#instanceChoice').val(),
							'yearly-income' : parseInt($('input[name=yearly-income]').val()),

						};

						// process the form
						var request = $.ajax({
							type : 'POST',
							url : 'bes-validate',
							data : JSON.stringify(formData),
							dataType : 'json',
						});

						request.done(function(data) {
							console.log(data);
							$('#info-panel').css('visibility', 'visible');
							if (!data.error) {
								if (data.approved == true) {
									$('#info-panel').attr('class','panel panel-success');
									$('#info-header').text('Approved')
									$('#info-text').html(format_json(data.response))

								} else if (data.approved == false) {
									$('#info-panel').attr('class','panel panel-warning');
									$('#info-header').text('Rejected')
									$('#info-text').html(format_json(data.response))
								}
							} else {
								if (data.error == true) {
									$('#info-text').text(data.text)

								} else {
									$('#info-text').text("An unkonw error occured")
								}

								$('#info-panel').attr('class','panel panel-danger');
								$('#info-header').text('Error')
							}
						});

						request.fail(function(jqXHR, textStatus) {
							console.log("Request failed: " + textStatus);
							$('#info-panel').attr('class','panel panel-error');
							$('#info-header').text('Error')
							$('#info-text').text(textStatus)
						});

					});

		});

function format_lines(messages) {
	var t = messages.map(function(l) {
		return l.line;
	});
	return t.join("</br>");
}

function format_json(obj) {
	var str = JSON.stringify(obj, null, 4 );
	return  "<p><code style=\"color:black;\"><b>" + str + "<\code><\p>";
	
}

function hide_panels() {
	$('#info-panel').css('visibility', 'hidden');
}