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

function Box(props: JSX.IntrinsicElements["mesh"]) {
  // This reference will give us direct access to the THREE.Mesh object
  const ref = useRef<THREE.Mesh>(null!);
  // Hold state for hovered and clicked events
  const [hovered, hover] = useState(false);
  const [clicked, click] = useState(false);
  // Rotate mesh every frame, this is outside of React without overhead
  useFrame((state, delta) => (ref.current.rotation.x += 0.01));

  return (
    <mesh
      {...props}
      ref={ref}
      scale={clicked ? 1.5 : 1}
      onClick={(event) => click(!clicked)}
      onPointerOver={(event) => hover(true)}
      onPointerOut={(event) => hover(false)}
    >
      <boxGeometry args={[1, 1, 1]} />
      <meshStandardMaterial color={hovered ? "hotpink" : "orange"} />
    </mesh>
  );
}

export const GraphicCanvas = () => {
  return (
      <Canvas>
        <ambientLight intensity={0.5} />
        <Whirligig position={[0,-30,-100]} scale={1}/>
        <Cat/>
        <OrthographicCamera />
        <OrbitControls />
      </Canvas>
  );
};
