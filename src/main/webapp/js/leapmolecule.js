/*
Supported Gestures:
    * Standard Leap gestures:
        - Swipe (left or right): enable spin
        - Circle (left or right): reset zoom level to initzoom
    * Cutom gestures:
        - Rotate
            * X-axis: right-hand roll control
            * Y-axis: both hands, pointing upwards as if grabbing a cube
                      and rotating it
            * Z-axis: left-hand roll control
        - Zoom
            * Pitch controlled for both hands (but only one at a time)
*/

// Data storage
let leftHand = {
    roll: 0,
    pitch: 0,
    yaw: 0,
    palm: [0, 0, 0]
};

let rightHand = {
    roll: 0,
    pitch: 0,
    yaw: 0,
    palm: [0, 0, 0]
};
let hands = [leftHand, rightHand];

// Spin is off by default
let spin = "off";
let debug = false;

// Makes working with angles easy
window.TO_RAD = Math.PI / 180;
window.TO_DEG = 1 / TO_RAD;

let controller = Leap.loop({enableGestures: true}, function(frame) {
  let str = "";
  for (let i in frame.handsMap) {
    let nhands = Object.keys(frame.handsMap).length;
    let hand = frame.handsMap[i];
    let index = hand.type == "left" ? 0 : 1;
    let gestures = frame.data.gestures;

    // Data Storage - for two hand gestures
    hands[index].roll = Math.round(hand.roll() * TO_DEG);
    hands[index].pitch = Math.round(hand.pitch() * TO_DEG);
    hands[index].yaw = Math.round(hand.yaw() * TO_DEG);
    hands[index].palm = hand.palmPosition;
    
    if (nhands == 1) {
      // Reset other hand if only one hand detected
      if (index == 0 && hands[1].pitch != 0)
        resetHand(hands[1]);
      else if (index == 1 && hands[0].pitch != 0)
        resetHand(hands[0]);
      
      // [GESTURE] RESET ZOOM & SPIN
      if ((gestures.length > 0) && (gestures[0].state === "stop")) {
        gestures.forEach(gesture => {
          if ((gesture.type === "circle") && (gesture.radius > 15)) {
            Jmol.script(jmolApplet0, `zoom ${initzoom}`);
            zoom = initzoom;
          } else if ((gesture.type === "swipe")) {
            spin = "on";
            Jmol.script(jmolApplet0, `spin ${spin}`);
          }
        });
      }
      
      // [GESTURE] ROTATE using roll (single hand)
      // Only works when the hand direction is reasonably flat
      if ((hands[index].pitch <= 0.4) && (hands[index].pitch >= -0.2)) {
        // Uses a deadzone of 10 or 20 degrees depending on directions
        // Note: direction is reversed
        let axis = index == 0 ? "z" : "x";
        if (hands[index].roll <= -20) {
          stopSpin();
          Jmol.script(jmolApplet0, `rotate ${axis} ${-hands[index].roll / 30}`);
        } else if (hands[index].roll >= 10) {
          stopSpin();
          Jmol.script(jmolApplet0, `rotate ${axis} ${-hands[index].roll / 15}`);
        }
      } else if ((hands[index].pitch >= 50) || (hands[index].pitch <= -20)) {
        // [GESTURE] ZOOM using pitch (single hand)
        if ((hands[index].pitch >= 50) && (hands[index].pitch < 70)) {
            Jmol.script(jmolApplet0, `zoom ${zoom += 0.5}`);
        } else if (hands[index].pitch >= 70) {
            Jmol.script(jmolApplet0, `zoom ${zoom++}`);
        } else if (hands[index].pitch <= -20) {
            Jmol.script(jmolApplet0, `zoom ${zoom -= 1}`);
        } 
      }
      // When two hands are detected
    } else if (nhands > 1) {
      let distance = calculateHandDistance(hands);
      
      // [GESTURE] ROTATE with TWO hands
      if ((nhands > 1) && (index == 0)) {
        if ((distance >= 100) && (distance <= 300)) {
          if (
            (hands[0].pitch >= 50) && (hands[0].roll >= 25) &&  // Left hand
            (hands[1].pitch >= 50) && (hands[1].roll <= -20)) { // Right hand                
            if ((hands[0].pitch >= 0.5) && (hands[1].pitch >= 0.5)) { // Both hands are pointing upwards
              Jmol.script(jmolApplet0, `rotate y ${calculateRotateAngle(hands) / 10}`);
            }
          }
        }
      }
    } else { // No hands, reset data
      resetHand(hand[0]);
      resetHand(hand[1]);
    }

    if (debug) {
      // Prints measured values for both hands on the webpage for inspection
      str += `<h4>${hand.type} - ${nhands} hands in view</h4><p>` +
          "<strong>Roll:</strong> " + hands[index].roll +
          "<br/><strong>Pitch:</strong> " + hands[index].pitch +
          "<br/><strong>Yaw:</strong> " + hands[index].yaw +
          "<br/><strong>Center:</strong> " + Math.round(hands[index].palm[0]) +
          "<br/><strong>Distance:</strong> " + calculateHandDistance(hands) +
          "</p>";
      document.getElementById('leap-debug').innerHTML = str;
    }
  }
  if (debug)
    document.getElementById('leap-debug').innerHTML = str;
});

/*
* Calculates a naive distance on the x-axis by calculating
* right - left hand center position
*/
function calculateHandDistance(hands) {
  return Math.round(hands[1].palm[0] - hands[0].palm[0]);
}

function resetHand(hand) {
  hand.pitch = 0;
  hand.roll = 0;
  hand.yaw = 0;
  hand.palm = [0, 0, 0]
}

function stopSpin() {
  if (spin === "on") {
    spin = "off";
    Jmol.script(jmolApplet0, `spin ${spin}`);
  }
}

/*
Calculates the angle between two points (hands) offset from the y-axis of the Leap
*/
function calculateRotateAngle(hands) {
  return Math.atan2(hands[1].palm[0] - hands[0].palm[0], 
                    hands[1].palm[1] - hands[0].palm[1]) * 180 / Math.PI - 90
}

/**
 * LEAP events
 */ 
controller.on('ready', function() {
    console.log("ready");
});
controller.on('connect', function() {
    console.log("connect");
});
controller.on('disconnect', function() {
    console.log("disconnect");
});
controller.on('focus', function() {
    console.log("focus");
});
controller.on('blur', function() {
    console.log("blur");
});
controller.on('deviceConnected', function() {
    console.log("deviceConnected");
});
controller.on('deviceDisconnected', function() {
    console.log("deviceDisconnected");
});