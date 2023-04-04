/* eslint-disable */
import { Canvas } from "@react-three/fiber";
import { Cat } from "./Cat";
import { Background } from "./background/Background";
import { OrbitControls, SpotLight } from "@react-three/drei";
import { cameraPos } from "./aniConfig";
import * as THREE from "three";
import { getUserConfig } from "@/connect/axios/queryHooks/user";

export const GraphicCanvas = () => {

  const { data } = getUserConfig();

  // let themeNum = 2;
  let themeNum = data ? data?.data.themeSeq : 1;

  let camera = { fov: 50, near: 0.1, far: 500, position: cameraPos[themeNum].default, scale:1 };
  let catPosition = new THREE.Vector3(0, -0.2, -0.7);
  let catRotation = new THREE.Euler(0,0,0);

  switch(themeNum) {
    case 2:
      catPosition = new THREE.Vector3(0.05, -0.4, -0.3);
      catRotation = new THREE.Euler(0,0.4,0);
      break;
    default:
      break;
  }

  return (
    <Canvas camera={camera as any}> 
      <ambientLight intensity={0.4} />
      <SpotLight position={[0,4,0]} intensity={2} angle={30} decay={1}/>
      <Cat themeNum={themeNum} scale={0.5} position={catPosition} rotation={catRotation}/>
      <Background themeNum={themeNum} position={new THREE.Vector3(0,-0.4,0)}/>
      <OrbitControls/>
    </Canvas>
  );
};
