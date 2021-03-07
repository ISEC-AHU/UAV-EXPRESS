# UAV-EXPRESS
* EXPRESS: An Energy-Efficient and Secure Framework for Mobile Edge Computing and Blockchain based Smart Systems
* In this paper, we propose EXPRESS, a novel energy-efficient and secure framework based on mobile edge computing and blockchain technologies. We focus on computation and data (resource) management which are two of the most prominent components in this framework. The effectiveness of the EXPRESS framework is demonstrated through the implementation of a real-world UAV delivery system. As an open-source framework, EXPRESS can help researchers implement their own prototypes and test their computation and data management strategies in different smart systems. The demo video can be found at https://youtu.be/r3U1iU8tSmk.
* An Energy-Aware Computation Management Strategy for MEC-based UAV Delivery System
* This paper investigates the energy aware multi-UAV task computation management problem in MEC-based UAV delivery service system according to a realistic Autonomous Delivery Network (ADNET). Specifically, we propose a computation management strategy, namely, MEC-based Task Offloading and Scheduling Strategy (TOSS), to provide an integral approach covering both the static task offloading and scheduling algorithm, as well as the dynamic resource conflict resolution algorithm. Grounded on real-world scenarios, our experimental results show that TOSS is capable of achieving higher payload for UAVs by using minimum energy consumption, task makespan within the given constraints of the deadline, compared to the state-of-the-art.
# Developer
* Developer organization:
1. ISEC laboratory, School of Computer Science and Technology, Anhui University, Hefei, China
2. School of Information Technology, Deakin University, Geelong, Australia
3. School of Software and Electrical Engineering, Swinburne University of Technology, Melbourne, Australia
4. Antwork Robotics Co., Ltm., Hangzhou, China
* Developer member: Jia Xu, Xiao Liu, Xuejun Li, Yun Yang, Lei Zhang, Han Gao, Lina Gong
# System Framework
![image](https://github.com/ISEC-AHU/UAV-EXPRESS/blob/master/Figure/Framework.jpg)
# The Autonomous Delivery Plan of Antwork Company
![image](https://github.com/ISEC-AHU/UAV-EXPRESS/blob/master/Figure/ADNET.jpg)
# The Distribution of UAV Airport and Base Station of Antwork Company
![image](https://github.com/ISEC-AHU/UAV-EXPRESS/blob/master/Figure/Map.jpg)
# Framework Evaluation
1. Energy consumption and makespan
![image](https://github.com/ISEC-AHU/UAV-EXPRESS/blob/master/Figure/energy.png)
![image](https://github.com/ISEC-AHU/UAV-EXPRESS/blob/master/Figure/makespan.png)
* The above illustration shows the results on the energy consumption and makespan of the UAV. The energy consumption of the ONLY-EDGE strategy is always the lowest. However, the gap between ONLY-EDGE and our MAWOSS strategy is small. For example, when the task number is 150, the energy consumption of MAWOSS is 3% lower than LOPRTC. However, if comparing their workflow makespan as shown in the next figure, we can find that the workflow makespan of ONLY-EDGE is much higher than MAWOSS. For example, when the task number is 150, the workflow task makespan of ONLY-EDGE is 14% higher than MAWOSS. This means ONLY-EDGE’s task offloading decision and scheduling plan may miss the given deadlines. In contrast, MAWOSS can always find the best task offloading decision and scheduling plan for reducing the energy consumption under the given deadlines.
2. Security analysis and discussion
* Here, we discuss how our proposed solution can effectively satisfy those major security and system requirements.
* 1)	High efficiency and robustness
There are many unresolved problems in the existing UAV delivery systems based on Cloud computing. The high latency brought by centralized Cloud computing is intolerable for UAVs flying in high speed. The system is also vulnerable to single point of failure since it closely depends on the Cloud. In order to effectively reduce the delay and avoid the occurrence of a single point of failure, our proposed system adopts the MEC namely an End-Edge-Cloud architecture. Under this architecture, the real-time response requirement of UAVs can be met because the service requests can be accommodated by nearby Edge servers. In addition, the system also has high robustness as the UAV can continue the delivery process by seamlessly switching to other Edge servers when a certain nearby Edge server fails.
* 2)	Identify malicious participant(s)
To implement authorization and authentication proposed in subsection 3.2, our system stores UAV information by Blockchain and verifies the UAV delivery permission by querying the Blockchain and using our proposed secure communication scheme when the UAV requests for delivery and relay delivery. An unqualified UAV will not have delivery permission, and a compromised UAV with an Ethereum account will not be able to easily obtain the corresponding private key. Therefore, the system can identify malicious delivery UAVs through the smart contracts of Blockchain.
* 3)	Integrity and traceability
In order to resolve disputes between the Sender, Receiver or Middler, UAV delivery systems should have fast and accurate traceability. In our system, all transactions between the Sender and Receiver, between the Sender and Middler will be recorded in the Blockchain. Due to the decentralization, tamper-proof and consistency nature of Bloc
# Full Paper Access
Access from <A href="https://drive.google.com/file/d/1VtUJY6IY4SOcLAz_690nTYgHxj3xCeQs/view?usp=sharing">https://drive.google.com/file/d/1VtUJY6IY4SOcLAz_690nTYgHxj3xCeQs/view?usp=sharing</A>

## IMPORTANT
Please check the `improv` branch for latest changes. Master branch has been left intact until complete testing.

## How to run UAV-EXPRESS ?

* The UAV-EXPRESS is divided into two parts:
1. The energy-efficient computation management mechanism by pose and face recognition application;
*	Create a Java project in Eclipse.
*	Inside the project directory, initialize an empty Git repository with the following command
* git init
*	Add the Git repository of project as the origin remote.
* git remote add origin <A href="https://github.com/ISEC-AHU/UAV-EXPRESS">https://github.com/ISEC-AHU/UAV-EXPRESS
*	Pull the contents of the repository to your machine.
* git pull origin master
*	Run the example files (e.g.device_expr.py; edge_expr.py) to get started.
* Development description
*	IDE: pycharm2019
*	Programming language:python 3.6; TensorFlow1.12.0
*	Video input: DJ Mavic drones capture video at 1080P
*	Configuration:
* Intel Core i5-4210M CPU，8GB DDR3L RAM(UAV);
* Intel Core i7-9750H CPU，16GB DDR4 RAM，NVIDIA GeForce GTX 1660Ti(edge sevser)

2. The data resource management module which integrates the technology of Blockchain and InterPlanetary File System (IPFS);
* Write and debug the required smart contracts in the ethereum remix- IDE;
* Compile the smart contracts using the smart contract compiler solc to obtain the .abi and.bin files(Remix compilation contracts are not recommended because the bytecode generated by the remix-IDE are buggy);
* Use the command-line tool web3j to convert the smart contracts into Java wrapper classes, and then placed them into eclipse's web project；
* Launch the ethereum client Ganache in Ubuntu and deploy the smart contracts in Ganache. Copy the account private key provided by Ganache and the contract address obtained after deployment into the eclipse web project to access the blockchain network and obtain the deployed smart contract;
* Start MongoDB and IPFS;
* Pre-set the account information of the delivery station administrator, airspace management department, UAV and recipients in the MongoDB database, and pre-set the order information to be delivered;
* To run the project, do the followings in order: select the project, right-click, click “run as”, click “run on Server”, and click “Finish”.
* Development description
* Eclipse 2018
* Ganache CLI v6.9.1
* Ubuntu16.04
* Solc v0.5.17
* Web3j v3.4.0
* MongoDB v4.2.3
* IPFS v0.4.23

# References
1.	Kai Yang, Qiang Li, and Limin Sun, 2019. Towards automatic fingerprinting of IoT devices in the cyberspace. Computer Networks 148 (Jan, 2019), 318-327. DOI:https://doi.org/10.1016/j.comnet.2018.11.013.
2.	Haicheng Chen, Wensheng Dou, Yanyan Jiang and Feng Qin, 2019. Understanding exception-related bugs in large-scale cloud systems. In Proceedings of the 34th IEEE/ACM International Conference on Automated Software Engineering (ASE). IEEE Press, Piscataway, NJ, 339-351. DOI:https://doi.org/10.1109/ASE.2019.00040.
3.	Giuseppe Aceto, Valerio Persico, and Antonio Pescapé, 2020. Industry 4.0 and Health: Internet of Things, Big Data, and Cloud Computing for Healthcare 4.0. Journal of Industrial Information Integration 18 (Jun, 2020), 1-13. DOI:https://doi.org/10.1016/j.jii.2020.100129.
4.	Ghulam Muhammad, SK Md Mizanur Rahman, Abdulhameed Alelaiwi and Atif Alamri, 2017. Smart health solution integrating IoT and cloud: A case study of voice pathology monitoring. IEEE Communications Magazine 55, 1 (Jan, 2017), 69-73. DOI:https://doi.org/10.1109/MCOM.2017.1600425CM.
5.	Ahmed, Ejaz, and Mubashir Husain Rehmani, 2017. Mobile edge computing: opportunities, solutions, and challenges. Future Generation Computer Systems 70 (May, 2017), 59-63. DOI:https://doi.org/10.1016/j.future.2016.09.015.
6.	Jianli Pan, Jianyu Wang, Austin Hester, Ismail Alqerm, Yuanni Liu and Ying Zhao, 2018. EdgeChain: An edge-IoT framework and prototype based on blockchain and smart contracts. IEEE Internet of Things Journal 6, 3 (Jun, 2018), 4719-4732. DOI: https://doi.org/10.1109/JIOT.2018.2878154
7.	Zehui Xiong, Yang Zhang, Dusit Niyato, Ping Wang and Zhu Han, 2018. When mobile blockchain meets edge computing. IEEE Communications Magazine 56, 8 (Aug, 2018), 33-39. DOI:https://doi.org/10.1109/MCOM.2018.1701095.
8.	Han Liu, Chao Liu, Wenqi Zhao, Yu Jiang and Jiaguang Sun, 2018. S-gram: towards semantic-aware security auditing for ethereum smart contracts. In Proceedings of the 33rd ACM/IEEE International Conference on Automated Software Engineering (ASE). ACM Press, New York, NY, 814-819. DOI: https://doi.org/10.1145/3238147.3240728.
9.	Xuesong Xu, Zhi Zeng, Shengjie Yang and Hongyan Shao, 2020. A novel blockchain framework for industrial IoT edge computing. Sensors 20, 7 (Feb, 2020), 1-16. DOI:https://doi.org/10.3390/s20072061.
10.	Xifan Yao, Jiajun Zhou, Yingzi Lin, Yun Li, Hongnian Yu and Ying Liu, 2019. Smart manufacturing based on cyber-physical systems and beyond. Journal of Intelligent Manufacturing 30, 8 (Dec, 2019), 2805-2817. DOI:https://doi.org/10.1007/s10845-017-1384-5.
11.	Fangxin Wang, Feng Wang, Xiaoqiang Ma and Jiangchuan Liu, 2019. Demystifying the crowd intelligence in last mile parcel delivery for smart cities. IEEE Network 33, 2 (Mar, 2019), 23-29. DOI:https://doi.org/ 10.1109/MNET.2019.1800228.
12.	Satoshi Tanaka, Taku Senoo, and Masatoshi Ishikawa, 2019. High-speed UAV delivery system with non-stop parcel handover using high-speed visual control. In Proceedings of the 2019 IEEE Intelligent Transportation Systems Conference (ITSC). IEEE Press, Piscataway, NJ, 4449-4455. DOI:https://doi.org/10.1109/ITSC.2019.8917296
13.	Jiawen Hu, Miao Jiang, Qi Zhang, Quanzhong L and Jiayin Qin, 2019. Joint optimization of UAV position, time slot allocation, and computation task partition in multiuser aerial mobile-edge computing systems. IEEE Transactions on Vehicular Technology 68, 7 (Jul, 2019), 7231-7235. DOI: https://doi.org/10.1109/TVT.2019.2915836.
14.	Jiao Zhang, Li Zhou, Qi Tang, Edith C.-H. Ngai, Xiping Hu, Haitao Zhao and Jibo Wei, 2018. Stochastic computation offloading and trajectory scheduling for UAV-assisted mobile edge computing. IEEE Internet of Things Journal 6, 2 (Apr, 2018), 3688-3699. DOI:https://doi.org/10.1109/JIOT.2018.2890133.
15.	Naser Hossein Motlagh, Miloud Bagaa and Tarik Taleb, 2019. Energy and delay aware task assignment mechanism for UAV-based IoT platform. IEEE Internet of Things Journal 6, 4 (Aug, 2019), 6523-6536. DOI:https://doi.org/10.1109/JIOT.2019.2907873.
16.	Hui Yang, Yongshen Liang, Jiaqi Yuan, Qiuyan Yao, Ao Yu, Jie Zhang, 2020. Distributed blockchain-based trusted multi-domain collaboration for mobile edge computing in 5G and beyond. IEEE Transactions on Industrial Informatics Early Access (Jan, 2020), 1-11. DOI:https://doi.org/ 10.1109/TII.2020.2964563.
17.	Mengting Liu, Yinglei Teng, F. Richard Yu, Victor C. M. Leung and Mei Song, 2020. A mobile edge computing (MEC)-enabled transcoding framework for blockchain-based video streaming. IEEE Wireless Communications 27, 2 (Apr, 2020), 81-87. DOI:https://doi.org/10.1109/MWC.001.1800332.
18.	Jia Xu, Xuejun Li, Xiao Liu, Chong Zhang, Lingmin Fan, Lina Gong and Juan Li, 2019. Mobility-aware workflow offloading and scheduling strategy for mobile edge computing. In Proceedings of the International Conference on Algorithms and Architectures for Parallel Processing. Springer, Cham, Switzerland, 184-199. DOI:https://doi.org/10.1007/978-3-030-38961-1_17.
