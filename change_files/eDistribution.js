            
            function backgroundChange() {
                var image = $(".dr1-image").attr("src");
                if (typeof image !== "undefined") {
                    $(".dr1-image").css("display", "none");
                    var css = {
                        "background": "url(" + image + ")",
                        "background-image": "url(" + image + ")"
                    };
                    $("#main").css(css);
                }
            }

            function numerationChange() {
                var cont = 0;
                $(".instruction-chevron").each(function () {
                    cont++;
                    var that = $(this);
                    that.text(" " + cont);
                    that.css({width: "auto"});
                }).css({"left": "-3.3rem"});
                $(".exercise-specific-instructions .instruction-row").css("margin-left", "2rem");
                $(".question-counter").css("display", "none");
                $(".instruction-chevron").css("line-height", "1.7rem");
            }

            function changeHelpCapital() {
                //console.log("asd");
                $(".help-bold-text").each(function () {
                    var that = $(this);
                    console.log(that.html());
                    var txt = that.html();
                    txt = txt.toLowerCase();
                    
                    var capitalized = txt[0].toUpperCase() + txt.substring(1);
                    console.log(capitalized);

                    that.html(capitalized);
                });
            }

            $('#index_html').watch('opacity', function () {
                backgroundChange();
                //numerationChange();
                changeHelpCapital();
            });