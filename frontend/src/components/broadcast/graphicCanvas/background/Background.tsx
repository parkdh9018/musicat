/* eslint-disable */
import * as THREE from "three";
import { GLTF } from "three/examples/jsm/loaders/GLTFLoader";
import { BackgroundStructure } from "./BackgroundStructure";
import { Objects } from "./Objects";
import { ExtraCat } from "./ExtraCat";
import { GroupProps } from "@react-three/fiber";

interface propsType extends GroupProps {
  themeNum: number;
  position: THREE.Vector3;
}

export const Background = ({ themeNum, position, ...props }: propsType) => {
  let extraCat_position = new THREE.Vector3(2.6, 0, 0.7);
  let extraCat_rotation = new THREE.Euler(0, -0.9, 0);
  let object_position = new THREE.Vector3(0, 0, 0);
  let backgroundStructre_rotation = new THREE.Euler(0, 0, 0);

  if (themeNum == 2) {
    object_position = new THREE.Vector3(0.65, -0.08, 0.13);
    extraCat_position = new THREE.Vector3(0.2, 0, -1.6);
    extraCat_rotation = new THREE.Euler(0, 0, 0);
  }

  return (
    <group position={position}>
      <Objects {...props} position={object_position} themeNum={themeNum} />
      <BackgroundStructure
        {...props}
        themeNum={themeNum}
        rotation={backgroundStructre_rotation}
      />
      <ExtraCat
        scale={0.5}
        position={extraCat_position}
        rotation={extraCat_rotation}
      />
    </group>
  );
};
