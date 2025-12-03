package handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class FileRemoveHandler {
    private static final Logger log = LoggerFactory.getLogger(FileRemoveHandler.class);

//    파일 경로, 파일 이름 => 파일 생성
//    생성한 파일과 같은 파일이 존재하면 삭제
//    경로, 파일 이름은 컨트롤에서 매개변수로 전달 => 리턴 isOk

    public boolean deleteFile(String path, String fileName){
        boolean isDel = false;

//        파일 객체 생성 / 썸네일 객체 생성
        File fileDir = new File(path);
        File removeFile = new File(fileDir+File.separator+fileName);
        File removeThumbFile = new File(fileDir+File.separator+"th_"+fileName);

//        파일 존재 여부 확인
        if(removeFile.exists()){
            isDel = removeFile.delete();
            log.info(">>> file is deleted : {}", isDel);

            if(isDel && removeThumbFile.exists()){
                isDel = removeThumbFile.delete();
                log.info(">>> thumb file is deleted : {}", isDel);
            }
        }

        return isDel;
    }
}
