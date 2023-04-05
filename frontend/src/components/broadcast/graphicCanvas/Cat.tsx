/* eslint-disable */
import { useAnimations, useGLTF, useTexture } from "@react-three/drei";
import { GroupProps, useFrame } from "@react-three/fiber";
import { useEffect, useMemo, useRef, useState } from "react";
import { GLTF } from "three/examples/jsm/loaders/GLTFLoader";
import { ani, cameraPos } from "./aniConfig";
import * as THREE from "three";
import { useRecoilValue } from "recoil";
import { broadcastState } from "@/atoms/broadcast.atom";

interface ExtendedGLTF extends GLTF {
  nodes: any;
}

interface propsType extends GroupProps {
  themeNum : number;
  position: THREE.Vector3; // ...
}

export const Cat = ({themeNum, position, ...props }: propsType) => {
  const broadcast = useRecoilValue(broadcastState);

  const defaultCameraPos = new THREE.Vector3().fromArray(cameraPos[themeNum].default);
  const chatCameraPos = new THREE.Vector3().fromArray(cameraPos[themeNum].chat);

  const speed = 0.2; // 움직임 속도를 조절하세요.
  const startPosition = new THREE.Vector3(
    position.x,
    position.y + 10,
    position.z
  ); // 시작 위치를 설정하세요.
  const targetPosition = position; // 목표 위치를 설정하세요.

  const { scene } = useGLTF("/graphic/cat/Chibi_Cat_01.glb") as ExtendedGLTF;

  const animations = Object.values(ani).reduce((acc: any, name) => {
    const { animations } = useGLTF(
      `/graphic/animation/${name}.glb`
    ) as ExtendedGLTF;
    acc.push(animations[0]);
    return acc;
  }, []);

  const { actions, mixer, names, ref } = useAnimations(animations);

  const playSequentialAnimations = () => {
    if (actions) {
      const jumpAction = actions[ani.Jump];
      const idleAction = actions[ani.Idle05];

      jumpAction?.reset().setLoop(THREE.LoopRepeat, 1).play();
      jumpAction?.crossFadeTo(idleAction as THREE.AnimationAction, 0.5, false);
      idleAction?.reset().fadeIn(0.5).play(); // Set the idle action to repeat infinitely
    }
  };

  useFrame(({ camera }) => {
    // console.log("postion : ",camera.position)

    if (broadcast.operation === "CHAT") {
      camera.position.lerp(chatCameraPos, 0.01);
    } else {
      camera.position.lerp(defaultCameraPos, 0.01);
    }

    if (ref.current) {
      // 객체의 위치와 목표 위치 사이의 거리를 계산합니다.
      const distance = ref.current.position.distanceTo(targetPosition);

      // 목표 위치에 도달하지 않았다면 객체를 움직입니다.
      if (distance > speed) {
        ref.current.position.lerp(targetPosition, speed);
      } else {
        // 목표 위치에 도착했다면 움직임을 중지합니다.
        ref.current.position.copy(targetPosition);
      }
    }
  });

  useEffect(() => {
    if (ref.current) {
      // 객체가 로드되면 시작 위치를 설정합니다.
      ref.current.position.copy(startPosition);
    }
    playSequentialAnimations();
  }, []);

  useEffect(() => {
    // console.log("operation : ", broadcast.operation);
    // console.log("dataType : ", broadcast.dataType);
    // console.log("datalength : ", broadcast.dataLength);


    actions[ani.Yes]?.fadeOut(0.5);
    actions[ani.Hi]?.fadeOut(0.5);
    actions[ani.Idle02]?.fadeOut(0.5);
    actions[ani.Idle05]?.fadeOut(0.5);

    // 말할때
    if (broadcast.dataType === "mp3") {
      // console.log("--말하는중")
      actions[ani.Yes]?.reset().setLoop(THREE.LoopRepeat, (broadcast.dataLength / 1000) - 1).fadeIn(0.5).play();
      actions[ani.Hi]?.reset().setLoop(THREE.LoopRepeat, (broadcast.dataLength / 1000) - 1).fadeIn(0.3).play();
      mixer.update(new THREE.Clock().getDelta())
      // 채팅 시간
    } else if (broadcast.operation === "CHAT") {
      // console.log("--소통시간")
      actions[ani.Idle02]?.reset().setLoop(THREE.LoopRepeat, Infinity).fadeIn(0.5).play();
      // 노래
    } else if (broadcast.dataType === "youtube") {
      // console.log("--노래듣는중")
      actions[ani.Yes]?.reset().setLoop(THREE.LoopRepeat, Infinity).setEffectiveTimeScale(0.3).fadeIn(0.5).play();
    } else {
      // console.log("--평소")
      actions[ani.Idle05]?.reset().setLoop(THREE.LoopRepeat, Infinity).fadeIn(0.5).play();
    }

  }, [broadcast]);

  const characterClick = () => {
    // console.log("click")
    // setIndex((prev) => (prev + 1) % names.length);
  };

  const characterPointerEnter = () => {
    // actions[ani.Yes]?.reset().setEffectiveTimeScale(0.8).fadeIn(0.5).play();
    actions[ani.Hi]?.reset().fadeIn(0.3).play();
  };

  const characterPointerLeave = () => {
    // actions[ani.Yes]?.fadeOut(0.5);
    actions[ani.Hi]?.fadeOut(0.5);
  };

  return (
    <group
      {...props}
      onClick={characterClick}
      onPointerEnter={characterPointerEnter}
      onPointerLeave={characterPointerLeave}
      ref={ref as any}
    >
      <primitive object={scene} />
    </group>
  );
};
