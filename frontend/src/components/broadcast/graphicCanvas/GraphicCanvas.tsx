/* eslint-disable */
import { Canvas, useFrame } from "@react-three/fiber";
import { Cat } from "./Cat";
import { Background } from "./background/Background";
import { OrbitControls, SpotLight } from "@react-three/drei";
import * as THREE from "three";

export const GraphicCanvas = () => {

  const cameraPosition = { fov: 50, near: 0.1, far: 500, position: [-1.34, 0.95, 1.70], scale:1 };
  const catPosition = new THREE.Vector3(0, -0.2, -0.7);
  const lightPosition = new THREE.Vector3(0, 1, 0);

  return (
    <Canvas camera={cameraPosition as any}> 
      <ambientLight intensity={0.4} />
      <SpotLight position={[0,4,0]} intensity={2} angle={30} decay={1}/>
      <Cat scale={0.5} position={catPosition}/>
      <Background num={1} position={new THREE.Vector3(0,-0.4,0)}/>
      <OrbitControls/>
    </Canvas>
  );
};
