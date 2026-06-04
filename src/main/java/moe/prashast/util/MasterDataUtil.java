package moe.prashast.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
@Component
public class MasterDataUtil {

    public static Map<Integer, String> getLanguage() {

        Map<Integer, String> map = new LinkedHashMap<>();

        map.put(1, "Assamese");
        map.put(2, "Bengali");
        map.put(3, "Gujarati");
        map.put(4, "Hindi");
        map.put(5, "Kannada");
        map.put(6, "Kashmiri");
        map.put(7, "Konkani");
        map.put(8, "Malayalam");
        map.put(9, "Manipuri");
        map.put(10, "Marathi");
        map.put(11, "Nepali");
        map.put(12, "Odia");
        map.put(13, "Punjabi");
        map.put(14, "Sanskrit");
        map.put(15, "Sindhi");
        map.put(16, "Tamil");
        map.put(17, "Telugu");
        map.put(18, "Urdu");
        map.put(19, "English");
        map.put(20, "Bodo");
        map.put(22, "Dogri");
        map.put(23, "Khasi");
        map.put(24, "Garo");
        map.put(25, "Mizo");
        map.put(26, "Bhutia");
        map.put(27, "Lepcha");
        map.put(28, "Limbo");
        map.put(29, "French");
        map.put(30, "Hmar");
        map.put(32, "Karbi");
        map.put(39, "Santhali");
        map.put(41, "Angami");
        map.put(42, "Ao");
        map.put(43, "Arabic");
        map.put(46, "German");
        map.put(47, "Kakbarak");
        map.put(48, "Konyak");
        map.put(49, "Bhoti");
        map.put(50, "Lotha");
        map.put(51, "Maithili");
        map.put(53, "Nicobaree");
        map.put(54, "Persian");
        map.put(55, "Portuguese");
        map.put(56, "Rajasthani");
        map.put(57, "Russian");
        map.put(58, "Sema");
        map.put(59, "Spanish");
        map.put(60, "Tibetan");
        map.put(61, "Zeliang");
        map.put(62, "Mundari");
        map.put(64, "Kuruk");
        map.put(65, "Ho");
        map.put(72, "Purgi");
        map.put(74, "Tamang");
        map.put(75, "Gurung");
        map.put(76, "Sherpa");
        map.put(77, "Rai");
        map.put(78, "MangAR");
        map.put(79, "Mukhia");
        map.put(80, "Newar");
        map.put(81, "Korean");
        map.put(82, "Japanese");
        map.put(96, "Alt-English");
        map.put(101, "Tangkhul");
        map.put(102, "Thadou");
        map.put(103, "Paite");
        map.put(104, "Zou");
        map.put(105, "Kom");
        map.put(106, "Vaiphei");
        map.put(107, "Mao");
        map.put(108, "Ruangmei");
        map.put(109, "Liangmei");
        map.put(111, "Gangte");
        map.put(112, "Simte");
        map.put(113, "Poula");
        map.put(114, "Anal");
        map.put(115, "Maring");
        map.put(116, "Maram");
        map.put(117, "Zeme");
        map.put(118, "GFC");
        map.put(119, "Nyishi");
        map.put(120, "Galo");
        map.put(121, "Tagin");
        map.put(122, "Wangcho");
        map.put(123, "Tangsa");
        map.put(124, "Idu");
        map.put(125, "Kaman");
        map.put(126, "Taraon");
        map.put(127, "Adi");
        map.put(128, "Chokri");
        map.put(129, "Khuzhale");
        map.put(130, "Chang");
        map.put(131, "Khiamniungan");
        map.put(132, "Kuki");
        map.put(133, "Phom");
        map.put(134, "Pochury");
        map.put(135, "Nethenyi");
        map.put(136, "Nzonkhwe");
        map.put(137, "Sangtam");
        map.put(138, "Yimkhiung");
        map.put(139, "Laica");
        map.put(140, "Chakma");
        map.put(141, "Mara");
        map.put(142, "Apatani");
        map.put(143, "Singpho");
        map.put(144, "Tutsa");
        map.put(145, "Tai-Khamti");

        return map;
    }



    public static Map<Integer, String> getCategory() {

        Map<Integer, String> map = new LinkedHashMap<>();

        map.put( 1,"Primary");                    // Primary School
        map.put( 2,"Upper Primary");                // Upper Primary School
        map.put(3,"Higher Secondary");       // Higher Secondary School
        map.put( 4,"Upper Primary");                    // Upper Primary School
        map.put(5,"Higher Secondary");           // Higher Secondary School
        map.put(6,"Secondary");            // Secondary School
        map.put(7,"Secondary");                // Secondary School
        map.put(8,"Secondary");                    // Secondary School
        map.put(10,"Higher Secondary");              // Higher Secondary School
        map.put(11,"Higher Secondary");                  // Higher Secondary School
        map.put(12,"Pre-Primary");                   // Pre-Primary School

        return map;
    }

    public static Map<Integer, String> getManagement() {

        Map<Integer, String> map = new LinkedHashMap<>();

        map.put(1, "Department of Education");
        map.put(2, "Tribal Welfare Department");
        map.put(3, "Local Body");
        map.put(4, "Government Aided");
        map.put(5, "Private Unaided (Recognized)");
        map.put(6, "Other State Govt. Managed");
        map.put(7, "Partially Govt. Aided");

        map.put(89, "Minority Affairs Department");
        map.put(90, "Social Welfare Department");
        map.put(91, "Ministry of Labour");

        map.put(92, "Kendriya Vidyalaya Sangathan");
        map.put(93, "Navodaya Vidyalaya Samiti");
        map.put(94, "Sainik School");
        map.put(95, "Railway School");
        map.put(96, "Central Tibetan School");

        map.put(97, "Madrasa Private Unaided (Recognized)");
        map.put(99, "Madrasa Aided (Recognized)");

        map.put(101, "Other Central Govt./PSU Schools");
        map.put(102, "Veda Schools / Gurukuls / Pathashalas (Aided/Unaided)");

        return map;
    }



    public static Map<Integer, String> getManagementStateWise() {
        Map<Integer, String> map = new LinkedHashMap<>();
        map.put(92, "Kendriya Vidyalaya Sangathan");
        map.put(93, "Navodaya Vidyalaya Samiti");
        map.put(94, "Sainik School");
        map.put(95, "Railway School");
        map.put(96, "Central Tibetan School");
        return map;
    }


    public static Map<Integer, String> getImpairment() {

        Map<Integer, String> impairment = new LinkedHashMap<>();

        impairment.put(1, "Blindness");
        impairment.put(2, "Low Vision");
        impairment.put(3, "Hearing Impairment");
        impairment.put(4, "Speech and Language");
        impairment.put(5, "Locomotor Disability");
        impairment.put(6, "Mental Illness");
        impairment.put(7, "Specific Learning Disabilities");
        impairment.put(8, "Cerebral Palsy");
        impairment.put(9, "Autism Spectrum Disorder");
        impairment.put(10, "Multiple Disability incl. Deaf & Blindness");
        impairment.put(11, "Leprosy Cured Students");
        impairment.put(12, "Dwarfism");
        impairment.put(13, "Intellectual Disability");
        impairment.put(14, "Muscular Dystrophy");
        impairment.put(15, "Chronic Neurological Conditions");
        impairment.put(16, "Multiple Sclerosis");
        impairment.put(17, "Thalassemia");
        impairment.put(18, "Haemophilia");
        impairment.put(19, "Sickle Cell Disease");
        impairment.put(20, "Acid Attack Victim");
        impairment.put(21, "Parkinson’s Disease");

        return impairment;
    }

    public static Map<Integer, String> getSchoolType(){
        Map<Integer,String> schoolType=new HashMap<>();
        schoolType.put(1,"Boys");
        schoolType.put(2,"Girls");
        schoolType.put(3,"Co-Educational");
        return schoolType;
    }

    public static Map<Integer,String> getReasonForAbsent(){
        Map<Integer,String> absentType=new HashMap<>();
        absentType.put(1,"Sick");
        absentType.put(2,"Family Function");
        absentType.put(3,"Personal Reason");
        absentType.put(4,"Emergency");
        absentType.put(5,"Unknown");
        return absentType;
    }

    public static Map<Integer,String> getTeacherType(){
        Map<Integer,String> teacherType= new HashMap<>();
        teacherType.put(1,"Head teacher");
        teacherType.put(2,"Acting Head teacher");
        teacherType.put(3,"Teacher");
        teacherType.put(5,"Instructor positioned as per RTE");
        teacherType.put(6,"Principal");
        teacherType.put(7,"Vice Principal");
        teacherType.put(8,"Lecturer");
        return teacherType;
    }

}
