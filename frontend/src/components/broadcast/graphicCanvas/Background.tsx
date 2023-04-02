/* eslint-disable */
import React, { useRef, useEffect } from 'react';
import { Canvas, GroupProps, useFrame } from '@react-three/fiber';
import * as THREE from 'three';
import { GLTF } from 'three/examples/jsm/loaders/GLTFLoader';

interface ExtendedGLTF extends GLTF {
  nodes: any;
}

export const Background = (props: GroupProps) => {
  const group = useRef();
  const [object, setObject] = React.useState<THREE.Object3D>();

  useEffect(() => {
    const loader = new THREE.ObjectLoader();
    loader.load('/broadcast/background/scene.json', (obj) => {
      return setObject(obj);
    });
  }, []);

  return (
    <group ref={group as any}>
      {object && <primitive object={object} />}
    </group>
  );
};