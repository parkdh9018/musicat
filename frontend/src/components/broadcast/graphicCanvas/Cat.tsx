/* eslint-disable */
import { useAnimations, useGLTF, useTexture } from "@react-three/drei";
import { GroupProps } from "@react-three/fiber";
import { useEffect, useRef } from "react";
import { Group } from "three";
import { GLTF } from "three/examples/jsm/loaders/GLTFLoader";

interface ExtendedGLTF extends GLTF {
  nodes: any;
}

interface propsType extends GroupProps {}

export const Cat = (props: propsType) => {

  const { scene, nodes } = useGLTF(
    "/broadcast/cat/Chibi_Cat_01.glb"
  ) as ExtendedGLTF;

  const { animations } = useGLTF(
    "broadcast/cat/anim/Anim_Chibi@Victory.glb"
  ) as ExtendedGLTF;

  const { ref, actions, names } = useAnimations(animations);


  useEffect(() => {
    actions[names[0]]?.play();
  }, [actions]);


  return (
    <group {...props} ref={ref as any}>
      <primitive object={scene} />
    </group>
  );
};
