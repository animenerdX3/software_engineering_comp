# software_engineering_comp

To run our video game, you must run the .EXE file and press play. Have fun! :) (We currently do not have an .EXE yet and you can't really play the game at all) 

Maybe one day, though. Like, in the future.

Instructions/larger description of game will be placed here, possibly with explanations of features, story, and characters.

Res folder holds the resources of the game

https://stackoverflow.com/questions/9997006/slick2d-and-jbox2d-how-to-draw?answertab=votes#tab-top

Updated flipping animation

Important Information:
- Antigravity pad is 10px tall
- Dialog text should start at 52px x 720px
- Name for dialog box should start at 52 x 659px
- Tutorial text should start at 30px x 25px

Hey its yannick
for animation, one idea is to make the mob an animation instead of an image

Changelog:
- Created Enemy
- Changed Height of Level Room To A More Realistic Height
- Created Projectile

TODO:
- Implement gravity and collision to enemy
- Implement collision to projectile
- Clean up projectiles, camera, and physics
- FIX COLLISION

HOW GRAVITY SHOULD WORK:
- Small constant variable that is divisible by 50.
- When jumping, the gravity should be made negative.
- When in the air, there should be a force constantly adding to it.
- When the player reaches a value divisible by 1 near the ground, set the gravity to 1.





Yannicks a goof:
// Javascript Document
var index = 0;

var images = ['media/aChristmasStory.jpg','media/elf.jpg','media/grinch.jpg','media/polarSpressSpress.jpg', 'media/frosty.jpg'];
var captions = ['A Christmas Story', 'Elf', 'How the Grinch Stole Christmas', 'The Polar Express', 'Frosty the Snowman'];

var the_image = document.getElementById("main-image");
the_image.src = images[0];

var the_indexNum = document.getElementById("currentIndex");
the_indexNum.innerHTML = index + 1;

var description = document.getElementById("description");
description.innerHTML = captions[0];

function show_image(direction)
{

  if (direction == "left")
  {
    index--;
  }
  else
  {
    index++;
    index %= images.length;
  }
  
  if (index < 0)
  {
    index = images.length - 1;
  }
  
	currentSlide(index);

}

function currentSlide(dotNum){
	index = dotNum;
	the_image.src = images[index];
    the_indexNum.innerHTML = index + 1;
    description.innerHTML = captions[index];
	document.getElementById(index).className += "active";
}



HTML:


