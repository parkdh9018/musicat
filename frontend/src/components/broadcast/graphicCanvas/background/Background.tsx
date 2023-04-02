/* eslint-disable */
import React, { useRef, useEffect } from 'react';
import { Canvas, GroupProps, useFrame } from '@react-three/fiber';
import * as THREE from 'three';
import { GLTF } from 'three/examples/jsm/loaders/GLTFLoader';
import { BackgroundStructure } from './type1/BackgroundStructure';
import { Objects } from './type1/Objects';

interface ExtendedGLTF extends GLTF {
  nodes: any;
}

export const Background = () => {

  return (
    <>
      <Objects/>
      <BackgroundStructure/>
    </>
  );
};