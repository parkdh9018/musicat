/* eslint-disable */
import React, { useRef, useEffect } from 'react';
import { Canvas, GroupProps, useFrame } from '@react-three/fiber';
import * as THREE from 'three';
import { GLTF } from 'three/examples/jsm/loaders/GLTFLoader';
import { useAnimations, useGLTF } from '@react-three/drei';

interface ExtendedGLTF extends GLTF {
  nodes: any;
}

interface ExtendedProps extends GroupProps {
  num : number;
}

export const Objects = ({num, ...props}: ExtendedProps) => {

  const { scene, animations } = useGLTF(`/graphic/background/${num}/objects.glb`) as ExtendedGLTF;
  const { ref } = useAnimations(animations);

  const position = new THREE.Vector3(0,0,0);

  const speed = 0.02; // 움직임 속도를 조절하세요.
  const startPosition = new THREE.Vector3(position.x, position.y + 10, position.z); // 시작 위치를 설정하세요.
  const targetPosition = position // 목표 위치를 설정하세요.


  useEffect(() => {
    if (ref.current) {
      // 객체가 로드되면 시작 위치를 설정합니다.
      ref.current.position.copy(startPosition);
    }
  }, []);
  
  useFrame(() => {
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

  return (
    <group ref={ref as any}>
      <primitive object={scene} />
    </group>
  );
};