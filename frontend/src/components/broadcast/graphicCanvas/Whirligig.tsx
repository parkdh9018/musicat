/* eslint-disable */
import { useAnimations, useGLTF, useTexture } from "@react-three/drei";
import { GroupProps } from "@react-three/fiber";
import { useEffect } from "react";
import { GLTF } from "three/examples/jsm/loaders/GLTFLoader";

interface ExtendedGLTF extends GLTF {
  nodes: any;
}

interface propsType extends GroupProps {}

export const Whirligig = (props: propsType) => {
  const texture = useTexture("/broadcast/Whirligig.png");
  const { nodes, animations } = useGLTF(
    "/broadcast/Whirligig.glb"
  ) as ExtendedGLTF;

  const { ref, actions, names } = useAnimations(animations);

  useEffect(() => {
    // console.log(actions)
    // console.log(actions[names[0]])
    actions[names[0]]?.play();
    // actions[names[0]].reset().fadeIn(0.5).play()
    // return () => actions[names[index]].fadeOut(0.5)
  }, [actions]);

  return (
    <group {...props} ref={ref as any}>
      <primitive object={nodes.Whirligig_root1} />
      <skinnedMesh
        castShadow
        receiveShadow
        geometry={nodes.mesh_0.geometry}
        skeleton={nodes.mesh_0.skeleton}
        scale={1}
      >
        <meshStandardMaterial map={texture} map-flipY={false} />
      </skinnedMesh>
    </group>
  );
};
