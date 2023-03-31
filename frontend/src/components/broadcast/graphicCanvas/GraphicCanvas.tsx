/* eslint-disable */
import * as THREE from "three";
import * as React from "react";
import { useRef, useState } from "react";
import { Canvas, useFrame } from "@react-three/fiber";
import {
  OrthographicCamera,
  OrbitControls,
  SpotLight,
} from "@react-three/drei";
import { Whirligig } from "./Whirligig";
import { Cat } from "./Cat";


export const GraphicCanvas = () => {
  return (
      <Canvas>
        <ambientLight intensity={0.5} />
        <Whirligig position={[0,-30,-100]} scale={1}/>
        <Cat scale={3} position={[0,-3,0]}/>
        <OrthographicCamera />
        <OrbitControls />
      </Canvas>
  );
};
