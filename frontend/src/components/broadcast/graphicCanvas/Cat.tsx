/* eslint-disable */
import { useAnimations, useGLTF, useTexture } from "@react-three/drei";
import { GroupProps, useFrame } from "@react-three/fiber";
import { useEffect, useMemo, useRef, useState } from "react";
import { GLTF } from "three/examples/jsm/loaders/GLTFLoader";
import { ANI_NAME } from "./ani_name";
import * as THREE from "three";
import { useRecoilValue } from "recoil";
import { broadcastOperationState } from "@/atoms/broadcast.atom";

interface ExtendedGLTF extends GLTF {
  nodes: any;
}

interface propsType extends GroupProps {
  position: THREE.Vector3;// ...
}

export const Cat = ({position, ...props}: propsType) => {

  const operation = useRecoilValue(broadcastOperationState);

  const speed = 0.2; // 움직임 속도를 조절하세요.
  const startPosition = new THREE.Vector3(position.x, position.y + 10, position.z); // 시작 위치를 설정하세요.
  const targetPosition = position // 목표 위치를 설정하세요.

  const { scene } = useGLTF("/graphic/cat/Chibi_Cat_01.glb") as ExtendedGLTF;

  const animations = ANI_NAME.reduce((acc: any, name) => {
    const { animations } = useGLTF(
      `/graphic/animation/Anim_Chibi@${name}.glb`
    ) as ExtendedGLTF;
    acc.push(animations[0]);
    return acc;
  }, []);

  const { actions, mixer, names, ref } = useAnimations(animations);

  const playSequentialAnimations = () => {
    if (actions) {
      const jumpAction = actions["Anim_Chibi@Jump"]
      const idleAction = actions["Anim_Chibi@Idle02"];
  
      jumpAction?.reset().setLoop(THREE.LoopRepeat, 1).play();
      jumpAction?.crossFadeTo(idleAction as THREE.AnimationAction, 0.5, false);
      idleAction?.play(); // Set the idle action to repeat infinitely
    }
  };

  useFrame(({camera}) => {
    // console.log(camera.position)
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
  })



  useEffect(() => {
    if (ref.current) {
      // 객체가 로드되면 시작 위치를 설정합니다.
      ref.current.position.copy(startPosition);
    }
    playSequentialAnimations();
  }, []);

  
  useEffect(() => {

    // console.log("operation : ", operation)

    actions["Anim_Chibi@Idle"]?.fadeOut(0.5);
    actions["Anim_Chibi@Yes"]?.fadeOut(0.5);
    actions["Anim_Chibi@Idle03"]?.fadeOut(0.5);
    actions["Anim_Chibi@Cute1"]?.fadeOut(0.5);


    if(operation === "CHAT") {
      actions["Anim_Chibi@Yes"]?.reset().fadeIn(0.5).play();
    } else if (operation === "MUSIC") {
      actions["Anim_Chibi@Idle03"]?.reset().fadeIn(0.5).play();
    } else if (operation === "IDLE") {
      actions["Anim_Chibi@Cute1"]?.reset().fadeIn(0.5).play();
    }
  },[operation])

  // useEffect(() => {
  //   actions["Anim_Chibi@Idle02"]?.reset().fadeIn(0.5).play();
  //   return () => {
  //     actions["Anim_Chibi@Idle02"]?.fadeOut(0.5);
  //   };
  // }, [index, actions, names]);

  const characterClick = () => {
    // console.log("click")
    // setIndex((prev) => (prev + 1) % names.length);
  };

  const characterPointerEnter = () => {
    actions["Anim_Chibi@Hi"]?.reset().fadeIn(0.5).play();
  };

  const characterPointerLeave = () => {
    actions["Anim_Chibi@Hi"]?.fadeOut(0.5);
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
